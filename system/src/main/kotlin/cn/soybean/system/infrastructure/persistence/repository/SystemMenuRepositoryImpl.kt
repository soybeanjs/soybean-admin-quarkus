package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemMenuEntity
import cn.soybean.domain.system.entity.SystemTenantEntity
import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.repository.SystemMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemMenuRepositoryImpl : SystemMenuRepository, PanacheRepositoryBase<SystemMenuEntity, String> {
    override fun all(): Uni<List<SystemMenuEntity>> = listAll()

    override fun allByUserId(userId: String): Uni<List<SystemMenuEntity>> = list(
        """
                    select m from SystemMenuEntity m
                    left join SystemRoleMenuEntity rm on m.id = rm.menuId
                    left join SystemRoleUserEntity ur on rm.roleId = ur.roleId
                    where ur.userId = ?1 and m.status = ?2
                """,
        userId,
        DbEnums.Status.ENABLED
    )

    override fun allByTenantId(tenantId: String): Uni<List<SystemMenuEntity>> =
        SystemTenantEntity.getTenantMenuIds(tenantId).flatMap { menuIds ->
            if (menuIds.isEmpty()) {
                Uni.createFrom().item(emptyList())
            } else {
                list("SELECT m FROM SystemMenuEntity m WHERE m.id IN ?1", menuIds)
            }
        }

    override fun saveOrUpdate(entity: SystemMenuEntity): Uni<SystemMenuEntity> = persist(entity)
    override fun getById(id: String): Uni<SystemMenuEntity> = findById(id)
    override fun delById(id: String): Uni<Boolean> = deleteById(id)
    override fun findAllByConstant(constant: Boolean): Uni<List<SystemMenuEntity>> = list("constant", constant)
    override fun allByTenantIdAndConstant(tenantId: String, constant: Boolean): Uni<List<SystemMenuEntity>> =
        SystemTenantEntity.getTenantMenuIds(tenantId).flatMap { menuIds ->
            if (menuIds.isEmpty()) {
                Uni.createFrom().item(emptyList())
            } else {
                list("SELECT m FROM SystemMenuEntity m WHERE m.constant = ?1 AND m.id IN ?2", constant, menuIds)
            }
        }
}
