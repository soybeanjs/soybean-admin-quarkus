package cn.soybean.shared.domain.aggregate

import java.time.LocalDateTime
import java.util.*

abstract class AggregateRoot(
    open val aggregateId: String,
    val aggregateType: String,
    var aggregateVersion: Long = 0
) {
    val changes: MutableList<AggregateEventEntity> = Collections.synchronizedList(mutableListOf())

    constructor(aggregateId: String, aggregateType: String) : this(aggregateId, aggregateType, 0)

    abstract fun whenCondition(eventEntity: AggregateEventEntity)

    fun apply(eventEntity: AggregateEventEntity) {
        validateEvent(eventEntity)
        eventEntity.aggregateType = this.aggregateType
        whenCondition(eventEntity)
        changes.add(eventEntity)
        aggregateVersion++
        eventEntity.aggregateVersion = this.aggregateVersion
    }

    fun raiseEvent(eventEntity: AggregateEventEntity) {
        validateEvent(eventEntity)
        eventEntity.aggregateType = this.aggregateType
        whenCondition(eventEntity)
        aggregateVersion++
    }

    private fun clearChanges() = changes.clear()

    fun toSnapshot() = clearChanges()

    private fun validateEvent(eventEntity: AggregateEventEntity) {
        if (eventEntity.aggregateId != this.aggregateId)
            throw RuntimeException("Invalid event entity: Event ${eventEntity.eventType} expected aggregateId ${this.aggregateId} but was ${eventEntity.aggregateId}")
    }

    protected fun createEvent(eventType: String, data: ByteArray, metaData: ByteArray?): AggregateEventEntity {
        val eventEntity = AggregateEventEntity()
        eventEntity.aggregateId = this.aggregateId
        eventEntity.aggregateType = this.aggregateType
        eventEntity.aggregateVersion = this.aggregateVersion
        eventEntity.eventType = eventType
        eventEntity.data = data
        eventEntity.metaData = metaData ?: byteArrayOf()
        eventEntity.timeStamp = LocalDateTime.now()
        return eventEntity
    }
}