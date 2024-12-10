/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemTenantEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni

interface SystemTenantRepository {
    fun findByName(name: String): Uni<SystemTenantEntity>

    fun getTenantList(query: String, params: Parameters): PanacheQuery<SystemTenantEntity>

    fun existsByName(name: String): Uni<Boolean>

    fun getById(id: String): Uni<SystemTenantEntity>

    fun saveOrUpdate(entity: SystemTenantEntity): Uni<SystemTenantEntity>
}
