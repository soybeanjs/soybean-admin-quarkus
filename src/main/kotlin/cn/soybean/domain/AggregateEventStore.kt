package cn.soybean.domain

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.domain.aggregate.AggregateSnapshotEntity
import cn.soybean.shared.eventsourcing.EventBus
import cn.soybean.shared.eventsourcing.EventSourcingUtils
import cn.soybean.shared.eventsourcing.EventSourcingUtils.aggregateFromSnapshot
import cn.soybean.shared.eventsourcing.EventStoreDB
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import java.lang.reflect.InvocationTargetException

@ApplicationScoped
class EventStore(private val eventBus: EventBus) : EventStoreDB {

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
            .invoke { _ -> Log.debugf("[EventStore] (saveEvents) Successfully saved %d events", eventEntityList.size) }
            .replaceWithUnit()
            .onFailure()
            .invoke { ex -> Log.errorf(ex, "[EventStore] (saveEvents) Error saving events. Details: %s", ex.message) }
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
                    "[EventStore] (loadEvents) Error querying events for aggregateId: %s, aggregateVersion: %d.",
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
                    .invoke { _ -> Log.debug("[EventStore] AFTER SAVE SNAPSHOT: Snapshot saved successfully") }

                else -> Uni.createFrom().nullItem<Unit>().onItem()
                    .invoke { _ -> Log.debug("[EventStore] SNAPSHOT NOT SAVED: Conditions not met for saving snapshot") }
            }
        }.flatMap { _ -> eventBus.publish(changesCopy) }
            .onItem()
            .invoke { _ -> Log.debug("[EventStore] AFTER EVENT BUS PUBLISH: Events published to event bus successfully") }
            .onFailure().invoke { ex ->
                Log.errorf(
                    ex,
                    "[EventStore] (save) Error during saving snapshot or publishing events to event bus. Error: %s",
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
            .invoke { ex -> Log.errorf(ex, "[EventStore] (saveSnapshot) Error executing preparedQuery.") }
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
            .invoke { ex -> Log.errorf(ex, "[EventStore] (getSnapshot) Error executing preparedQuery.") }
            .map { result ->
                when (result) {
                    null -> {
                        Log.debugf("[EventStore] (getSnapshot) No snapshot found for aggregateId: %s", aggregateId)
                        null
                    }

                    else -> {
                        Log.debugf("[EventStore] (getSnapshot) Snapshot aggregateVersion: %d", result.aggregateVersion)
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
                "[EventStore] (exists) Error checking existence for aggregateId: %s.",
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
            aggregateFromSnapshot(defaultSnapshot, aggregateType)
        }

        else -> aggregateFromSnapshot(snapshot.toAggregateSnapshotEntity(), aggregateType)
    }

    @WithSpan
    fun <T : AggregateRoot> raiseAggregateEvents(aggregate: T, events: List<AggregateEventEntity>): Uni<T> =
        when {
            events.isNotEmpty() -> {
                events.forEach { event ->
                    aggregate.raiseEvent(event)
                    Log.debugf("[EventStore] (raiseAggregateEvents) Event aggregateVersion: %d", event.aggregateVersion)
                }
                Uni.createFrom().item(aggregate)
            }

            else -> when (aggregate.aggregateVersion) {
                0L -> Uni.createFrom().failure(RuntimeException(aggregate.aggregateId))
                else -> Uni.createFrom().item(aggregate)
            }
        }
}

fun AggregateEventEntity.toEventEntity(): EventEntity = EventEntity().apply {
    aggregateId = this@toEventEntity.aggregateId
    aggregateType = this@toEventEntity.aggregateType
    aggregateVersion = this@toEventEntity.aggregateVersion
    eventType = this@toEventEntity.eventType
    data = this@toEventEntity.data
    metaData = this@toEventEntity.metaData
    timeStamp = this@toEventEntity.timeStamp
}

fun EventEntity.toAggregateEventEntity(): AggregateEventEntity = AggregateEventEntity().apply {
    aggregateId = this@toAggregateEventEntity.aggregateId
    aggregateType = this@toAggregateEventEntity.aggregateType
    aggregateVersion = this@toAggregateEventEntity.aggregateVersion
    eventType = this@toAggregateEventEntity.eventType
    data = this@toAggregateEventEntity.data
    metaData = this@toAggregateEventEntity.metaData
    timeStamp = this@toAggregateEventEntity.timeStamp
}

fun AggregateSnapshotEntity.toSnapshotEntity(): SnapshotEntity = SnapshotEntity().apply {
    aggregateId = this@toSnapshotEntity.aggregateId
    aggregateType = this@toSnapshotEntity.aggregateType
    aggregateVersion = this@toSnapshotEntity.aggregateVersion
    data = this@toSnapshotEntity.data
    metaData = this@toSnapshotEntity.metaData
    timeStamp = this@toSnapshotEntity.timeStamp
}

fun SnapshotEntity.toAggregateSnapshotEntity(): AggregateSnapshotEntity = AggregateSnapshotEntity().apply {
    aggregateId = this@toAggregateSnapshotEntity.aggregateId
    aggregateType = this@toAggregateSnapshotEntity.aggregateType
    aggregateVersion = this@toAggregateSnapshotEntity.aggregateVersion
    data = this@toAggregateSnapshotEntity.data
    metaData = this@toAggregateSnapshotEntity.metaData
    timeStamp = this@toAggregateSnapshotEntity.timeStamp
}