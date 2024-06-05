package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.event.tenant.TenantUpdatedEventBase
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantUpdatedProjection(private val tenantRepository: SystemTenantRepository) : Projection {

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
                }
                tenantRepository.saveOrUpdate(tenant)
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantUpdatedEventBase.TENANT_UPDATED_V1
}

