package cn.soybean.domain

import cn.soybean.domain.EventSourcingUtils.aggregateFromSnapshot
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import java.lang.reflect.InvocationTargetException
import java.time.LocalDateTime

interface EventStoreDB {
    fun saveEvents(eventEntities: List<EventEntity>): Uni<Unit>

    fun loadEvents(aggregateId: String, version: Long): Uni<List<EventEntity>>

    fun <T : AggregateRoot> save(aggregate: T): Uni<Unit>

    fun <T : AggregateRoot> load(aggregateId: String, aggregateType: Class<T>): Uni<T>

    fun exists(aggregateId: String): Uni<Boolean>
}

@ApplicationScoped
class EventStore(private val eventBus: EventBus) : EventStoreDB {

    private val snapshotFrequency = 3

    @WithSpan
    override fun saveEvents(eventEntities: List<EventEntity>): Uni<Unit> {
        val eventEntityList = eventEntities.map { event ->
            EventEntity().apply {
                aggregateId = event.aggregateId
                aggregateType = event.aggregateType
                eventType = event.eventType
                data = event.data
                metaData = event.metaData ?: byteArrayOf()
                version = event.version
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
    override fun loadEvents(aggregateId: String, version: Long): Uni<List<EventEntity>> =
        EventEntity.find("aggregateId = ?1 and version > ?2", Sort.by("version"), aggregateId, version)
            .list()
            .onFailure().invoke { ex ->
                Log.errorf(
                    ex,
                    "[EventStore] (loadEvents) Error querying events for aggregateId: %s, version: %d.",
                    aggregateId,
                    version
                )
            }

    @WithSpan
    override fun <T : AggregateRoot> save(aggregate: T): Uni<Unit> {
        val changesCopy = aggregate.changes.toMutableList()
        return saveEvents(aggregate.changes).flatMap { _ ->
            when {
                aggregate.version % snapshotFrequency == 0L -> saveSnapshot(aggregate).onItem()
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
        return SnapshotEntity.persist(snapshot)
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
                    aggregate.version
                ).flatMap { events -> raiseAggregateEvents(aggregate, events) }
            }

    @WithSpan
    fun getSnapshot(aggregateId: String): Uni<SnapshotEntity?> =
        SnapshotEntity.find("aggregateId", Sort.by(AggregateConstants.VERSION, Sort.Direction.Descending), aggregateId)
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
                        Log.debugf("[EventStore] (getSnapshot) Snapshot version: %d", result.version)
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

        else -> aggregateFromSnapshot(snapshot, aggregateType)
    }

    @WithSpan
    fun <T : AggregateRoot> raiseAggregateEvents(aggregate: T, events: List<EventEntity>): Uni<T> =
        when {
            events.isNotEmpty() -> {
                events.forEach { event ->
                    aggregate.raiseEvent(event)
                    Log.debugf("[EventStore] (raiseAggregateEvents) Event version: %d", event.version)
                }
                Uni.createFrom().item(aggregate)
            }

            else -> when (aggregate.version) {
                0L -> Uni.createFrom().failure(RuntimeException(aggregate.aggregateId))
                else -> Uni.createFrom().item(aggregate)
            }
        }
}

object EventSourcingUtils {

    fun <T : AggregateRoot> snapshotFromAggregate(aggregate: T): SnapshotEntity {
        val bytes = SerializerUtils.serializeToJsonBytes(aggregate)
        val snapshotEntity = SnapshotEntity()
        snapshotEntity.aggregateId = aggregate.aggregateId
        snapshotEntity.aggregateType = aggregate.type
        snapshotEntity.version = aggregate.version
        snapshotEntity.data = bytes
        snapshotEntity.timeStamp = LocalDateTime.now()
        return snapshotEntity
    }

    fun <T : AggregateRoot> aggregateFromSnapshot(snapshotEntity: SnapshotEntity, valueType: Class<T>): T =
        SerializerUtils.deserializeFromJsonBytes(snapshotEntity.data, valueType)
}