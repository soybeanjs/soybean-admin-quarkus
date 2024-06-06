package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemRoleApiEntity
import io.smallrye.mutiny.Uni

interface SystemRoleApiRepository {
    fun saveOrUpdateAll(roleOperations: List<SystemRoleApiEntity>): Uni<Unit>
    fun findOperationIds(tenantId: String, roleCode: String): Uni<List<SystemRoleApiEntity>>
    fun removeOperationsByTenantId(tenantId: String, operationIds: Set<String>): Uni<Long>
}
