/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.event.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent
import java.time.LocalDateTime

data class TenantCreatedEventBase(
    val aggregateId: String,
    val name: String,
    val contactUserId: String,
    val contactAccountName: String,
    val status: DbEnums.Status,
    val website: String? = null,
    val expireTime: LocalDateTime,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null,
) : AggregateEventBase(aggregateId),
    DomainEvent {
    companion object {
        const val TENANT_CREATED_V1 = "TENANT_CREATED_V1"
    }
}
