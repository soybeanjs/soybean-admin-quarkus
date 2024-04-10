package cn.soybean.shared.domain.aggregate

import java.time.LocalDateTime

class AggregateEventEntity {
    lateinit var aggregateId: String
    lateinit var aggregateType: String
    var aggregateVersion: Long = 0
    lateinit var eventType: String
    lateinit var data: ByteArray
    var metaData: ByteArray? = null
    lateinit var timeStamp: LocalDateTime
}