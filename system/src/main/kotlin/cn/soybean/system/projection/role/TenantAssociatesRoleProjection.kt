package cn.soybean.system.projection.role

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.config.DbConstants
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.entity.SystemRoleUserEntity
import cn.soybean.system.domain.event.tenant.TenantCreatedEventBase
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.domain.repository.SystemRoleUserRepository
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantAssociatesRoleProjection(
    private val roleRepository: SystemRoleRepository,
    private val roleUserRepository: SystemRoleUserRepository,
    private val roleMenuRepository: SystemRoleMenuRepository
) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, TenantCreatedEventBase::class.java)
        val roleEntity = SystemRoleEntity(
            name = DbConstants.SUPER_TENANT_ROLE_CODE,
            code = DbConstants.SUPER_TENANT_ROLE_CODE,
            order = 1,
            status = DbEnums.Status.ENABLED,
            remark = "system generated"
        ).also {
            it.id = YitIdHelper.nextId().toString()
            it.tenantId = event.aggregateId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }
        return roleRepository.saveOrUpdate(roleEntity)
            .flatMap { role ->
                val tenantId = role?.tenantId
                val userId = event.contactUserId
                when {
                    tenantId != null -> {
                        val roleUser = SystemRoleUserEntity(role.id, userId, tenantId)
                        roleUserRepository.saveOrUpdate(roleUser).replaceWith(role)
                    }

                    else -> Uni.createFrom().item(role)
                }
            }
            .flatMap { role ->
                when {
                    event.menuIds.isNullOrEmpty() -> Uni.createFrom().item(role)

                    else -> event.tenantId?.let { tenantId ->
                        val roleMenus = event.menuIds.map { menuId -> SystemRoleMenuEntity(role.id, menuId, tenantId) }

                        roleMenuRepository.delByRoleId(role.id, tenantId)
                            .flatMap { _ -> roleMenuRepository.saveOrUpdateAll(roleMenus) }
                    }
                }
            }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantCreatedEventBase.TENANT_CREATED_V1
}
