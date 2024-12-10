/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemUserEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni

interface SystemUserRepository {
    fun findByAccountNameOrEmailOrPhoneNumber(accountName: String, tenantId: String): Uni<SystemUserEntity>

    fun getUserList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemUserEntity>

    fun getById(id: String, tenantId: String): Uni<SystemUserEntity?>

    fun getByAccountName(accountName: String, tenantId: String): Uni<SystemUserEntity?>

    fun getByPhoneNumber(phoneNumber: String, tenantId: String): Uni<SystemUserEntity?>

    fun getByEmail(email: String, tenantId: String): Uni<SystemUserEntity?>

    fun saveOrUpdate(entity: SystemUserEntity): Uni<SystemUserEntity>

    fun delById(id: String, tenantId: String): Uni<Long>

    fun findByTenantId(tenantId: String): Uni<List<SystemUserEntity>>
}
