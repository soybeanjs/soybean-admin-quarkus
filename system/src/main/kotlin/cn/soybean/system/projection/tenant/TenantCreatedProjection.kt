package cn.soybean.system.projection.tenant

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.aggregate.user.bcryptHashPassword
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.event.tenant.TenantCreatedEventBase
import cn.soybean.system.domain.repository.SystemTenantRepository
import cn.soybean.system.domain.repository.SystemUserRepository
import cn.soybean.system.domain.vo.TenantContactPassword
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantCreatedProjection(
    private val tenantRepository: SystemTenantRepository,
    private val userRepository: SystemUserRepository
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
            accountPassword = event.createAccountName?.let { bcryptHashPassword(TenantContactPassword.genPass(it)) },
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

        return tenantRepository.saveOrUpdate(tenant).flatMap { userRepository.saveOrUpdate(user) }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantCreatedEventBase.TENANT_CREATED_V1
}
