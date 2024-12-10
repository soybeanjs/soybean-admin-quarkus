/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.eventsourcing

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import io.smallrye.mutiny.Uni

interface EventStoreDB {
    fun saveEvents(eventEntities: MutableList<AggregateEventEntity>): Uni<Unit>

    fun loadEvents(aggregateId: String, aggregateVersion: Long): Uni<List<AggregateEventEntity>>

    fun <T : AggregateRoot> save(aggregate: T): Uni<Unit>

    fun <T : AggregateRoot> load(aggregateId: String, aggregateType: Class<T>): Uni<T>

    fun exists(aggregateId: String): Uni<Boolean>
}
