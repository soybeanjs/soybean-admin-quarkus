/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemRoleApiEntity
import io.smallrye.mutiny.Uni

interface SystemRoleApiRepository {
    fun saveOrUpdateAll(roleOperations: List<SystemRoleApiEntity>): Uni<Unit>

    fun findOperationIds(
        tenantId: String,
        roleCode: String,
    ): Uni<List<SystemRoleApiEntity>>

    fun removeOperationsByTenantId(
        tenantId: String,
        operationIds: Set<String>,
    ): Uni<Long>

    fun delByRoleId(
        roleId: String,
        tenantId: String,
    ): Uni<Long>
}
