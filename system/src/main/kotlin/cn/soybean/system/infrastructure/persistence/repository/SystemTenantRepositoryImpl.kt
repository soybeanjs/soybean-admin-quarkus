/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemTenantEntity
import cn.soybean.domain.system.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantRepositoryImpl : SystemTenantRepository, PanacheRepositoryBase<SystemTenantEntity, String> {
    override fun findByName(name: String): Uni<SystemTenantEntity> = find("name", name).singleResult()

    override fun getTenantList(query: String, params: Parameters): PanacheQuery<SystemTenantEntity> = find(query, params)

    override fun existsByName(name: String): Uni<Boolean> = find("name", name).count().map {
        when {
            it > 0 -> true
            else -> false
        }
    }

    override fun getById(id: String): Uni<SystemTenantEntity> = findById(id)

    override fun saveOrUpdate(entity: SystemTenantEntity): Uni<SystemTenantEntity> = persist(entity)
}
