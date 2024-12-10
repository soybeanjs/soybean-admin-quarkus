/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.event.role

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class RoleCreatedOrUpdatedEventBase(
    val aggregateId: String,
    val name: String,
    val code: String,
    val order: Int,
    val status: DbEnums.Status,
    val dataScope: DbEnums.DataPermission? = null,
    val dataScopeDeptIds: Set<String>? = null,
    val remark: String? = null,
    val userId: String? = null,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null,
) : AggregateEventBase(aggregateId),
    DomainEvent {
    companion object {
        const val ROLE_CREATED_V1 = "ROLE_CREATED_V1"
        const val ROLE_TENANT_ASSOCIATES_V1 = "ROLE_TENANT_ASSOCIATES_V1"
        const val ROLE_UPDATED_V1 = "ROLE_UPDATED_V1"
    }
}
