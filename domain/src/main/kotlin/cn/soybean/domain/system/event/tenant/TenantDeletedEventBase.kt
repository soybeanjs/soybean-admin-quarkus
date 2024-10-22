package cn.soybean.domain.system.event.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class TenantDeletedEventBase(
    val aggregateId: String,
    val status: DbEnums.Status
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val TENANT_DELETED_V1 = "TENANT_DELETED_V1"
    }
}
