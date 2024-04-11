package cn.soybean.system.domain.repository

import io.smallrye.mutiny.Uni

interface SystemRoleMenuRepository {
    fun delByRoleId(roleId: String, tenantId: String): Uni<Long>
}