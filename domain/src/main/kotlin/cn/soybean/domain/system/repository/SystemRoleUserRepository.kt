package cn.soybean.domain.system.repository

import cn.soybean.domain.system.entity.SystemRoleUserEntity
import io.smallrye.mutiny.Uni

interface SystemRoleUserRepository {
    fun delByRoleId(roleId: String, tenantId: String): Uni<Long>
    fun delByUserId(userId: String, tenantId: String): Uni<Long>
    fun saveOrUpdate(roleUser: SystemRoleUserEntity): Uni<SystemRoleUserEntity>
    fun saveOrUpdateAll(roleUsers: List<SystemRoleUserEntity>): Uni<Unit>
}
