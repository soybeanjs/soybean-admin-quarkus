package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemRoleApiEntity
import io.smallrye.mutiny.Uni

interface SystemRoleApiRepository {
    fun saveOrUpdateAll(roleOperations: List<SystemRoleApiEntity>): Uni<Unit>
    fun findOperationIds(tenantId: String, roleCode: String): Uni<List<SystemRoleApiEntity>>
    fun removeOperationsByTenantId(tenantId: String, operationIds: Set<String>): Uni<Long>
    fun delByRoleId(roleId: String, tenantId: String): Uni<Long>
}
