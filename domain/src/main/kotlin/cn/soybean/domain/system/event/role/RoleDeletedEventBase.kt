/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.event.role

import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class RoleDeletedEventBase(
    val aggregateId: String,
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROLE_DELETED_V1 = "ROLE_DELETED_V1"
    }
}
