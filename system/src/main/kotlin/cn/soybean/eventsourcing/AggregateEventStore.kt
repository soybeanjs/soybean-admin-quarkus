package cn.soybean.eventsourcing

import cn.soybean.domain.aggregate.AggregateConstants
import cn.soybean.eventsourcing.entity.EventEntity
import cn.soybean.eventsourcing.entity.SnapshotEntity
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.eventsourcing.EventBus
import cn.soybean.shared.eventsourcing.EventSourcingUtils
import cn.soybean.shared.eventsourcing.EventStoreDB
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import java.lang.reflect.InvocationTargetException

@ApplicationScoped
class AggregateEventStore(private val eventBus: EventBus) : EventStoreDB {

    private val snapshotFrequency = 3

    @WithSpan
    override fun saveEvents(eventEntities: MutableList<AggregateEventEntity>): Uni<Unit> {
        val eventEntityList = eventEntities.map { event ->
            EventEntity().apply {
                aggregateId = event.aggregateId
                aggregateType = event.aggregateType
                aggregateVersion = event.aggregateVersion
                eventType = event.eventType
                data = event.data
                metaData = event.metaData ?: byteArrayOf()
                timeStamp = event.timeStamp
            }
        }

        return EventEntity.persist(eventEntityList)
            .onItem()
            .invoke { _ ->
                Log.debugf(
                    "[AggregateEventStore] (saveEvents) Successfully saved %d events",
                    eventEntityList.size
                )
            }
            .replaceWithUnit()
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "[AggregateEventStore] (saveEvents) Error saving events. Details: %s",
                    ex.message
                )
            }
    }

    @WithSpan
    override fun loadEvents(aggregateId: String, aggregateVersion: Long): Uni<List<AggregateEventEntity>> =
        EventEntity.find(
            "aggregateId = ?1 and aggregateVersion > ?2",
            Sort.by("aggregateVersion"),
            aggregateId,
            aggregateVersion
        )
            .list()
            .map { it.map { entity -> entity.toAggregateEventEntity() } }
            .onFailure().invoke { ex ->
                Log.errorf(
                    ex,
                    "[AggregateEventStore] (loadEvents) Error querying events for aggregateId: %s, aggregateVersion: %d.",
                    aggregateId,
                    aggregateVersion
                )
            }

    @WithSpan
    override fun <T : AggregateRoot> save(aggregate: T): Uni<Unit> {
        val changesCopy = aggregate.changes.toMutableList()
        return saveEvents(aggregate.changes).flatMap { _ ->
            when {
                aggregate.aggregateVersion % snapshotFrequency == 0L -> saveSnapshot(aggregate).onItem()
                    .invoke { _ -> Log.debug("[AggregateEventStore] AFTER SAVE SNAPSHOT: Snapshot saved successfully") }

                else -> Uni.createFrom().nullItem<Unit>().onItem()
                    .invoke { _ -> Log.debug("[AggregateEventStore] SNAPSHOT NOT SAVED: Conditions not met for saving snapshot") }
            }
        }.flatMap { _ -> eventBus.publish(changesCopy) }
            .onItem()
            .invoke { _ -> Log.debug("[AggregateEventStore] AFTER EVENT BUS PUBLISH: Events published to event bus successfully") }
            .onFailure().invoke { ex ->
                Log.errorf(
                    ex,
                    "[AggregateEventStore] (save) Error during saving snapshot or publishing events to event bus. Error: %s",
                    ex.message
                )
            }
            .replaceWithUnit()
    }

    @WithSpan
    fun <T : AggregateRoot> saveSnapshot(aggregate: T): Uni<Unit> {
        aggregate.toSnapshot()
        val snapshot = EventSourcingUtils.snapshotFromAggregate(aggregate)
        return SnapshotEntity.persist(snapshot.toSnapshotEntity())
            .onFailure()
            .invoke { ex -> Log.errorf(ex, "[AggregateEventStore] (saveSnapshot) Error executing preparedQuery.") }
            .replaceWithUnit()
    }

    @WithSpan
    override fun <T : AggregateRoot> load(aggregateId: String, aggregateType: Class<T>): Uni<T> =
        getSnapshot(aggregateId)
            .map { snapshot -> getSnapshotFromClass(snapshot, aggregateId, aggregateType) }
            .flatMap { aggregate ->
                loadEvents(
                    aggregate.aggregateId,
                    aggregate.aggregateVersion
                ).flatMap { events -> raiseAggregateEvents(aggregate, events) }
            }

    @WithSpan
    fun getSnapshot(aggregateId: String): Uni<SnapshotEntity?> =
        SnapshotEntity.find(
            "aggregateId",
            Sort.by(AggregateConstants.AGGREGATE_VERSION, Sort.Direction.Descending),
            aggregateId
        )
            .firstResult()
            .onFailure()
            .invoke { ex -> Log.errorf(ex, "[AggregateEventStore] (getSnapshot) Error executing preparedQuery.") }
            .map { result ->
                when (result) {
                    null -> {
                        Log.debugf(
                            "[AggregateEventStore] (getSnapshot) No snapshot found for aggregateId: %s",
                            aggregateId
                        )
                        null
                    }

                    else -> {
                        Log.debugf(
                            "[AggregateEventStore] (getSnapshot) Snapshot aggregateVersion: %d",
                            result.aggregateVersion
                        )
                        result
                    }
                }
            }

    @WithSpan
    override fun exists(aggregateId: String): Uni<Boolean> = EventEntity.count("aggregateId", aggregateId)
        .map { count -> count > 0 }
        .onFailure().invoke { ex ->
            Log.errorf(
                ex,
                "[AggregateEventStore] (exists) Error checking existence for aggregateId: %s.",
                aggregateId
            )
        }

    @WithSpan
    fun <T : AggregateRoot> getAggregate(aggregateId: String, aggregateType: Class<T>): T = try {
        aggregateType.getConstructor(String::class.java).newInstance(aggregateId)
    } catch (e: InstantiationException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    } catch (e: InvocationTargetException) {
        throw RuntimeException(e)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException(e)
    }

    @WithSpan
    fun <T : AggregateRoot> getSnapshotFromClass(
        snapshot: SnapshotEntity?,
        aggregateId: String,
        aggregateType: Class<T>
    ): T = when (snapshot) {
        null -> {
            val defaultSnapshot = EventSourcingUtils.snapshotFromAggregate(getAggregate(aggregateId, aggregateType))
            EventSourcingUtils.aggregateFromSnapshot(defaultSnapshot, aggregateType)
        }

        else -> EventSourcingUtils.aggregateFromSnapshot(snapshot.toAggregateSnapshotEntity(), aggregateType)
    }

    @WithSpan
    fun <T : AggregateRoot> raiseAggregateEvents(aggregate: T, events: List<AggregateEventEntity>): Uni<T> =
        when {
            events.isNotEmpty() -> {
                events.forEach { event ->
                    aggregate.raiseEvent(event)
                    Log.debugf(
                        "[AggregateEventStore] (raiseAggregateEvents) Event aggregateVersion: %d",
                        event.aggregateVersion
                    )
                }
                Uni.createFrom().item(aggregate)
            }

            else -> when (aggregate.aggregateVersion) {
                0L -> Uni.createFrom().failure(RuntimeException(aggregate.aggregateId))
                else -> Uni.createFrom().item(aggregate)
            }
        }
}