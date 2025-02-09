/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.eventsourcing.entity

import cn.soybean.domain.aggregate.AggregateConstants
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonProperty
import java.time.LocalDateTime

@MongoEntity(collection = AggregateConstants.EVENT)
class EventEntity : ReactivePanacheMongoEntity() {
    companion object : ReactivePanacheMongoCompanion<EventEntity>

    @field:BsonProperty(AggregateConstants.AGGREGATE_ID)
    lateinit var aggregateId: String

    @field:BsonProperty(AggregateConstants.AGGREGATE_TYPE)
    lateinit var aggregateType: String

    @field:BsonProperty(AggregateConstants.AGGREGATE_VERSION)
    var aggregateVersion: Long = 0

    @field:BsonProperty(AggregateConstants.EVENT_TYPE)
    lateinit var eventType: String

    @field:BsonProperty(AggregateConstants.DATA)
    lateinit var data: ByteArray

    @field:BsonProperty(AggregateConstants.METADATA)
    var metaData: ByteArray? = null

    @field:BsonProperty(AggregateConstants.TIMESTAMP)
    lateinit var timeStamp: LocalDateTime

    override fun toString(): String =
        "EventEntity(" +
            "eventType='$eventType', " +
            "aggregateId='$aggregateId', " +
            "aggregateType='$aggregateType', " +
            "aggregateVersion=$aggregateVersion, " +
            "data=${data.contentToString()}, " +
            "metaData=${metaData?.contentToString()}, " +
            "timeStamp=$timeStamp" +
            ")"
}
