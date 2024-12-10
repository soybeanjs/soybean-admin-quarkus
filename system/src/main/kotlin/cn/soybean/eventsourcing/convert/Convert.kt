/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.eventsourcing.convert

import cn.soybean.eventsourcing.entity.EventEntity
import cn.soybean.eventsourcing.entity.SnapshotEntity
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateSnapshotEntity

fun AggregateEventEntity.toEventEntity(): EventEntity = EventEntity().also {
    it.aggregateId = this.aggregateId
    it.aggregateType = this.aggregateType
    it.aggregateVersion = this.aggregateVersion
    it.eventType = this.eventType
    it.data = this.data
    it.metaData = this.metaData
    it.timeStamp = this.timeStamp
}

fun EventEntity.toAggregateEventEntity(): AggregateEventEntity = AggregateEventEntity().also {
    it.aggregateId = this.aggregateId
    it.aggregateType = this.aggregateType
    it.aggregateVersion = this.aggregateVersion
    it.eventType = this.eventType
    it.data = this.data
    it.metaData = this.metaData
    it.timeStamp = this.timeStamp
}

fun AggregateSnapshotEntity.toSnapshotEntity(): SnapshotEntity = SnapshotEntity().also {
    it.aggregateId = this.aggregateId
    it.aggregateType = this.aggregateType
    it.aggregateVersion = this.aggregateVersion
    it.data = this.data
    it.metaData = this.metaData
    it.timeStamp = this.timeStamp
}

fun SnapshotEntity.toAggregateSnapshotEntity(): AggregateSnapshotEntity = AggregateSnapshotEntity().also {
    it.aggregateId = this.aggregateId
    it.aggregateType = this.aggregateType
    it.aggregateVersion = this.aggregateVersion
    it.data = this.data
    it.metaData = this.metaData
    it.timeStamp = this.timeStamp
}
