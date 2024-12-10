/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemRoleApiEntity
import cn.soybean.domain.system.repository.SystemRoleApiRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleApiRepositoryImpl :
    SystemRoleApiRepository,
    PanacheRepositoryBase<SystemRoleApiEntity, String> {
    override fun saveOrUpdateAll(roleOperations: List<SystemRoleApiEntity>): Uni<Unit> = persist(roleOperations).replaceWithUnit()

    override fun findOperationIds(
        tenantId: String,
        roleCode: String,
    ): Uni<List<SystemRoleApiEntity>> =
        list(
            """
            SELECT ra
            FROM SystemRoleApiEntity ra
            LEFT JOIN SystemRoleEntity r ON r.id = ra.roleId
            WHERE ra.tenantId = ?1 AND r.code = ?2
        """,
            tenantId,
            roleCode,
        )

    override fun removeOperationsByTenantId(
        tenantId: String,
        operationIds: Set<String>,
    ): Uni<Long> = delete("tenantId = ?1 and operationId in ?2", tenantId, operationIds)

    override fun delByRoleId(
        roleId: String,
        tenantId: String,
    ): Uni<Long> = delete("roleId = ?1 and tenantId = ?2", roleId, tenantId)
}
