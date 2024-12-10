/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemMenuEntity
import io.smallrye.mutiny.Uni

interface SystemMenuRepository {
    fun all(): Uni<List<SystemMenuEntity>>

    fun allByUserId(userId: String): Uni<List<SystemMenuEntity>>

    fun allByTenantId(tenantId: String): Uni<List<SystemMenuEntity>>

    fun saveOrUpdate(entity: SystemMenuEntity): Uni<SystemMenuEntity>

    fun getById(id: String): Uni<SystemMenuEntity>

    fun delById(id: String): Uni<Boolean>

    fun findAllByConstant(constant: Boolean): Uni<List<SystemMenuEntity>>

    fun allByTenantIdAndConstant(
        tenantId: String,
        constant: Boolean,
    ): Uni<List<SystemMenuEntity>>
}
