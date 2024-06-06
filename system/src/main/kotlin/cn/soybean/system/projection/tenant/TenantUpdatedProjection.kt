package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.config.DbConstants.SUPER_TENANT_ROLE_CODE
import cn.soybean.system.domain.entity.SystemRoleApiEntity
import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.event.tenant.TenantUpdatedEventBase
import cn.soybean.system.domain.repository.SystemRoleApiRepository
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantUpdatedProjection(
    private val tenantRepository: SystemTenantRepository,
    private val roleRepository: SystemRoleRepository,
    private val roleMenuRepository: SystemRoleMenuRepository,
    private val roleApiRepository: SystemRoleApiRepository
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
                    event.menuIds.isNullOrEmpty() -> Uni.createFrom().item(tenant)
                    else -> processMenuUpdate(tenant.id, event.menuIds).replaceWith(tenant)
                }
            }.flatMap { tenant ->
                when {
                    event.operationIds.isNullOrEmpty() -> Uni.createFrom().nullItem()
                    else -> processOperationUpdate(tenant.id, event.operationIds)
                }
            }.replaceWithUnit()
    }

    private fun processMenuUpdate(tenantId: String, menuIds: Set<String>): Uni<Unit> {
        return roleMenuRepository.findMenuIds(tenantId, SUPER_TENANT_ROLE_CODE)
            .flatMap { menuEntities ->
                val adminRoleId = menuEntities.firstOrNull()?.roleId
                when {
                    adminRoleId.isNullOrBlank() -> {
                        roleRepository.getByCode(tenantId, SUPER_TENANT_ROLE_CODE).flatMap {
                            val menusToAdd = menuIds.map { menuId ->
                                SystemRoleMenuEntity(roleId = it.id, menuId = menuId, tenantId = tenantId)
                            }
                            roleMenuRepository.saveOrUpdateAll(menusToAdd)
                        }
                    }

                    else -> {
                        val existingMenuIds = menuEntities.mapNotNull { it.menuId }.toSet()
                        syncAdminMenuIds(tenantId, adminRoleId, existingMenuIds, menuIds)
                    }
                }
            }
    }

    private fun processOperationUpdate(tenantId: String, operationIds: Set<String>): Uni<Unit> {
        return roleApiRepository.findOperationIds(tenantId, SUPER_TENANT_ROLE_CODE)
            .flatMap { apiEntities ->
                val adminRoleId = apiEntities.firstOrNull()?.roleId
                when {
                    adminRoleId.isNullOrBlank() -> {
                        roleRepository.getByCode(tenantId, SUPER_TENANT_ROLE_CODE).flatMap {
                            val operationsToAdd = operationIds.map { operationId ->
                                SystemRoleApiEntity(roleId = it.id, operationId = operationId, tenantId = tenantId)
                            }
                            roleApiRepository.saveOrUpdateAll(operationsToAdd)
                        }
                    }

                    else -> {
                        val existingOperationIds = apiEntities.mapNotNull { it.operationId }.toSet()
                        syncAdminOperationIds(tenantId, adminRoleId, existingOperationIds, operationIds)
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

    private fun syncAdminOperationIds(
        tenantId: String,
        roleId: String,
        existingOperationIds: Set<String>,
        newOperationIds: Set<String>
    ): Uni<Unit> {
        val operationsToAdd = newOperationIds.subtract(existingOperationIds).map { operationId ->
            SystemRoleApiEntity(roleId = roleId, operationId = operationId, tenantId = tenantId)
        }

        val operationsToRemove = existingOperationIds.subtract(newOperationIds)

        return roleApiRepository.saveOrUpdateAll(operationsToAdd)
            .flatMap { roleApiRepository.removeOperationsByTenantId(tenantId, operationsToRemove) }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantUpdatedEventBase.TENANT_UPDATED_V1
}

