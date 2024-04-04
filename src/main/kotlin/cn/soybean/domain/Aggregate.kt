package cn.soybean.domain

import java.time.LocalDateTime
import java.util.concurrent.CopyOnWriteArrayList

abstract class BaseAggregateEvent(aggregateId: String) {
    init {
        require(aggregateId.isNotBlank()) { "BaseAggregateEvent aggregateId is required" }
    }
}

object AggregateConstants {
    const val EVENT: String = "event"
    const val SNAPSHOT: String = "snapshot"

    const val EVENT_TYPE: String = "event_type"
    const val AGGREGATE_ID: String = "aggregate_id"
    const val AGGREGATE_TYPE: String = "aggregate_type"
    const val VERSION: String = "version"
    const val DATA: String = "data"
    const val METADATA: String = "metadata"
    const val TIMESTAMP: String = "timestamp"
}

abstract class AggregateRoot(
    open val aggregateId: String,
    val type: String,
    var version: Long = 0
) {
    val changes: MutableList<EventEntity> = CopyOnWriteArrayList()

    constructor(aggregateId: String, aggregateType: String) : this(aggregateId, aggregateType, 0)

    abstract fun whenCondition(eventEntity: EventEntity)

    fun apply(eventEntity: EventEntity) {
        validateEvent(eventEntity)
        eventEntity.aggregateType = this.type

        whenCondition(eventEntity)
        changes.add(eventEntity)

        version++
        eventEntity.version = this.version
    }

    fun raiseEvent(eventEntity: EventEntity) {
        validateEvent(eventEntity)

        eventEntity.aggregateType = this.type
        whenCondition(eventEntity)

        version++
    }

    private fun clearChanges() = changes.clear()

    fun toSnapshot() = clearChanges()

    fun string() = "aggregateId: {$aggregateId}, type: {$type}, version: {$version}, changes: {${changes.size}}"

    private fun validateEvent(eventEntity: EventEntity) {
        if (eventEntity.aggregateId == this.aggregateId) return
        throw RuntimeException("Invalid event entity: $eventEntity")
    }

    protected fun createEvent(eventType: String, data: ByteArray, metaData: ByteArray?): EventEntity {
        val eventEntity = EventEntity()
        eventEntity.eventType = eventType
        eventEntity.aggregateId = this.aggregateId
        eventEntity.aggregateType = this.type
        eventEntity.version = this.version
        eventEntity.data = data
        eventEntity.metaData = metaData ?: byteArrayOf()
        eventEntity.timeStamp = LocalDateTime.now()
        return eventEntity
    }

    override fun toString() =
        "AggregateRoot(aggregateId='$aggregateId', type='$type', version=$version, changes=${changes.size})"
}