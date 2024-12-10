/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemRoleEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni

interface SystemRoleRepository {
    fun getRoleCodesByUserId(userId: String): Uni<Set<String>>

    fun getRoleList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemRoleEntity>

    fun saveOrUpdate(entity: SystemRoleEntity): Uni<SystemRoleEntity>

    fun getById(id: String, tenantId: String): Uni<SystemRoleEntity?>

    fun existsByCode(code: String, tenantId: String): Uni<Boolean>

    fun delById(id: String, tenantId: String): Uni<Long>

    fun getByCode(tenantId: String, code: String): Uni<SystemRoleEntity>
}
