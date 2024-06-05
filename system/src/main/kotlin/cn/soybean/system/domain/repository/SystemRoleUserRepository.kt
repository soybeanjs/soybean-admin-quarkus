package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemRoleUserEntity
import io.smallrye.mutiny.Uni

interface SystemRoleUserRepository {
    fun delByRoleId(roleId: String, tenantId: String): Uni<Long>
    fun saveOrUpdate(roleUser: SystemRoleUserEntity): Uni<SystemRoleUserEntity>
}
