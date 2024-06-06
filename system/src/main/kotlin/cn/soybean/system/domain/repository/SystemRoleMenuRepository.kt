package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import io.smallrye.mutiny.Uni

interface SystemRoleMenuRepository {
    fun delById(roleId: String, menuId: String, tenantId: String): Uni<Long>
    fun delByMenuId(menuId: String, tenantId: String): Uni<Long>
    fun delByRoleId(roleId: String, tenantId: String): Uni<Long>
    fun saveOrUpdate(roleMenu: SystemRoleMenuEntity): Uni<SystemRoleMenuEntity>
    fun saveOrUpdateAll(roleMenus: List<SystemRoleMenuEntity>): Uni<Unit>
    fun findMenuIds(tenantId: String, roleCode: String): Uni<List<SystemRoleMenuEntity>>
    fun removeMenusByTenantId(tenantId: String, menuIds: Set<String>): Uni<Long>
}
