package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.event.tenant.TenantCreatedEventBase
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantCreatedProjection(private val tenantRepository: SystemTenantRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, TenantCreatedEventBase::class.java)
        val systemTenantEntity = SystemTenantEntity(
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
        return tenantRepository.saveOrUpdate(systemTenantEntity).replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantCreatedEventBase.TENANT_CREATED_V1
}