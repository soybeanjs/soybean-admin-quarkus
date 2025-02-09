/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.eventsourcing

import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.domain.aggregate.AggregateSnapshotEntity
import cn.soybean.shared.util.SerializerUtils
import java.time.LocalDateTime

object EventSourcingUtils {
    fun <T : AggregateRoot> snapshotFromAggregate(aggregate: T): AggregateSnapshotEntity {
        val bytes = SerializerUtils.serializeToJsonBytes(aggregate)
        val snapshotEntity = AggregateSnapshotEntity()
        snapshotEntity.aggregateId = aggregate.aggregateId
        snapshotEntity.aggregateType = aggregate.aggregateType
        snapshotEntity.aggregateVersion = aggregate.aggregateVersion
        snapshotEntity.data = bytes
        snapshotEntity.timeStamp = LocalDateTime.now()
        return snapshotEntity
    }

    fun <T : AggregateRoot> aggregateFromSnapshot(
        snapshotEntity: AggregateSnapshotEntity,
        valueType: Class<T>,
    ): T = SerializerUtils.deserializeFromJsonBytes(snapshotEntity.data, valueType)
}
