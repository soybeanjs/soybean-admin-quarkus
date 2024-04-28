package cn.soybean.system.projection.tenant

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.system.domain.event.tenant.TenantDeletedEventBase
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantDeletedProjection : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        TODO()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantDeletedEventBase.TENANT_DELETED_V1
}