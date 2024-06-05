package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleMenuRepositoryImpl : SystemRoleMenuRepository, PanacheRepositoryBase<SystemRoleMenuEntity, String> {
    override fun delById(roleId: String, menuId: String, tenantId: String): Uni<Long> =
        delete("roleId = ?1 and menuId = ?2 and tenantId = ?3", roleId, menuId, tenantId)

    override fun delByMenuId(menuId: String, tenantId: String): Uni<Long> =
        delete("menuId = ?1 and tenantId = ?2", menuId, tenantId)

    override fun delByRoleId(roleId: String, tenantId: String): Uni<Long> =
        delete("roleId = ?1 and tenantId = ?2", roleId, tenantId)

    override fun saveOrUpdate(roleMenu: SystemRoleMenuEntity): Uni<SystemRoleMenuEntity> = persist(roleMenu)
    override fun saveOrUpdateAll(roleMenus: List<SystemRoleMenuEntity>): Uni<Unit> =
        persist(roleMenus).replaceWithUnit()
}
