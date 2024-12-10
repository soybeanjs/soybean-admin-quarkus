/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemRoleUserEntity
import io.smallrye.mutiny.Uni

interface SystemRoleUserRepository {
    fun delByRoleId(
        roleId: String,
        tenantId: String,
    ): Uni<Long>

    fun delByUserId(
        userId: String,
        tenantId: String,
    ): Uni<Long>

    fun saveOrUpdate(roleUser: SystemRoleUserEntity): Uni<SystemRoleUserEntity>

    fun saveOrUpdateAll(roleUsers: List<SystemRoleUserEntity>): Uni<Unit>
}
