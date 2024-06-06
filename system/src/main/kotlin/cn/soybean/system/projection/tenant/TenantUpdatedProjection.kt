package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.config.DbConstants.SUPER_TENANT_ROLE_CODE
import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.event.tenant.TenantUpdatedEventBase
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantUpdatedProjection(
    private val tenantRepository: SystemTenantRepository,
    private val roleMenuRepository: SystemRoleMenuRepository
) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, TenantUpdatedEventBase::class.java)
        return tenantRepository.getById(event.aggregateId)
            .flatMap { tenant ->
                tenant.also {
                    tenant.name = event.name
                    tenant.status = event.status
                    tenant.website = event.website
                    tenant.expireTime = event.expireTime
                    tenant.menuIds = event.menuIds
                    tenant.operationIds = event.operationIds
                    tenant.updateBy = event.updateBy
                    tenant.updateAccountName = event.updateAccountName
                }
                tenantRepository.saveOrUpdate(tenant)
            }
            .flatMap { tenant ->
                when {
                    event.menuIds.isNullOrEmpty() -> Uni.createFrom().nullItem()
                    else -> processMenuUpdate(tenant.id, event.menuIds)
                }
            }.replaceWithUnit()
    }

    private fun processMenuUpdate(tenantId: String, menuIds: Set<String>): Uni<Unit> {
        return roleMenuRepository.findMenuIds(tenantId, SUPER_TENANT_ROLE_CODE)
            .flatMap { menuEntities ->
                val adminRoleId = menuEntities.firstOrNull()?.roleId
                when {
                    adminRoleId.isNullOrBlank() -> Uni.createFrom().nullItem()
                    else -> {
                        val existingMenuIds = menuEntities.mapNotNull { it.menuId }.toSet()
                        syncAdminMenuIds(tenantId, adminRoleId, existingMenuIds, menuIds)
                    }
                }
            }
    }

    private fun syncAdminMenuIds(
        tenantId: String,
        roleId: String,
        existingMenuIds: Set<String>,
        newMenuIds: Set<String>
    ): Uni<Unit> {
        val menusToAdd = newMenuIds.subtract(existingMenuIds).map { menuId ->
            SystemRoleMenuEntity(roleId = roleId, menuId = menuId, tenantId = tenantId)
        }

        val menusToRemove = existingMenuIds.subtract(newMenuIds)

        return roleMenuRepository.saveOrUpdateAll(menusToAdd)
            .flatMap { roleMenuRepository.removeMenusByTenantId(tenantId, menusToRemove) }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantUpdatedEventBase.TENANT_UPDATED_V1
}

