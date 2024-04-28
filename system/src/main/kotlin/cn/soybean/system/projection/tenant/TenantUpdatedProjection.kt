package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.system.domain.event.tenant.TenantUpdatedEventBase
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantUpdatedProjection : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        TODO()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantUpdatedEventBase.TENANT_UPDATED_V1
}

