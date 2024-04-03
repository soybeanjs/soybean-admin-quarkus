package cn.soybean.domain

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonProperty
import java.time.LocalDateTime

@MongoEntity(collection = AggregateConstants.SNAPSHOT)
class SnapshotEntity : ReactivePanacheMongoEntity() {
    companion object : ReactivePanacheMongoCompanion<SnapshotEntity>

    @field:BsonProperty(AggregateConstants.AGGREGATE_ID)
    lateinit var aggregateId: String

    @field:BsonProperty(AggregateConstants.AGGREGATE_TYPE)
    lateinit var aggregateType: String

    @field:BsonProperty(AggregateConstants.VERSION)
    var version: Long = 0

    @field:BsonProperty(AggregateConstants.DATA)
    lateinit var data: ByteArray

    @field:BsonProperty(AggregateConstants.METADATA)
    var metaData: ByteArray? = null

    @field:BsonProperty(AggregateConstants.TIMESTAMP)
    lateinit var timeStamp: LocalDateTime
}