package cn.soybean.system.projection.tenant

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.aggregate.user.bcryptHashPassword
import cn.soybean.system.domain.config.DbConstants
import cn.soybean.system.domain.entity.SystemRoleApiEntity
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.entity.SystemRoleUserEntity
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.event.tenant.TenantCreatedEventBase
import cn.soybean.system.domain.repository.SystemRoleApiRepository
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.domain.repository.SystemRoleUserRepository
import cn.soybean.system.domain.repository.SystemTenantRepository
import cn.soybean.system.domain.repository.SystemUserRepository
import cn.soybean.system.domain.vo.TenantContactPassword
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantCreatedProjection(
    private val tenantRepository: SystemTenantRepository,
    private val userRepository: SystemUserRepository,
    private val roleRepository: SystemRoleRepository,
    private val roleUserRepository: SystemRoleUserRepository,
    private val roleMenuRepository: SystemRoleMenuRepository,
    private val roleApiRepository: SystemRoleApiRepository
) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, TenantCreatedEventBase::class.java)
        val tenant = SystemTenantEntity(
            name = event.name,
            contactUserId = event.contactUserId,
            contactAccountName = event.contactAccountName,
            status = event.status,
            website = event.website,
            expireTime = event.expireTime,
            menuIds = event.menuIds,
            operationIds = event.operationIds
        ).also {
            it.id = event.aggregateId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }

        val user = SystemUserEntity(
            accountName = event.contactAccountName,
            accountPassword = bcryptHashPassword(TenantContactPassword.genPass(event.contactAccountName)),
            nickName = event.contactAccountName,
            personalProfile = "system generated",
            countryCode = DbEnums.CountryInfo.CN.countryCode,
            phoneCode = DbEnums.CountryInfo.CN.phoneCode,
            status = DbEnums.Status.ENABLED
        ).also {
            it.id = event.contactUserId
            it.tenantId = event.aggregateId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }

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

        return tenantRepository.saveOrUpdate(tenant).flatMap { userRepository.saveOrUpdate(user) }
            .flatMap { processAssociatesRole(roleEntity, event) }
            .replaceWithUnit()
    }

    private fun processAssociatesRole(
        roleEntity: SystemRoleEntity,
        event: TenantCreatedEventBase,
    ): Uni<Unit> = roleRepository.saveOrUpdate(roleEntity)
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

                else -> {
                    val roleMenus =
                        event.menuIds.map { menuId -> SystemRoleMenuEntity(role.id, menuId, event.aggregateId) }

                    roleMenuRepository.saveOrUpdateAll(roleMenus).replaceWith(role)
                }
            }
        }
        .flatMap { role ->
            when {
                event.operationIds.isNullOrEmpty() -> Uni.createFrom().nullItem()

                else -> {
                    val roleOperations =
                        event.operationIds.map { operationId ->
                            SystemRoleApiEntity(
                                role.id,
                                operationId,
                                event.aggregateId
                            )
                        }

                    roleApiRepository.saveOrUpdateAll(roleOperations).replaceWith(role)
                }
            }
        }.replaceWithUnit()

    override fun supports(eventType: String): Boolean = eventType == TenantCreatedEventBase.TENANT_CREATED_V1
}
