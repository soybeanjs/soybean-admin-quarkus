/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.eventsourcing

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import io.smallrye.mutiny.Uni

fun interface EventBus {
    fun publish(eventEntities: MutableList<AggregateEventEntity>): Uni<Unit>
}
