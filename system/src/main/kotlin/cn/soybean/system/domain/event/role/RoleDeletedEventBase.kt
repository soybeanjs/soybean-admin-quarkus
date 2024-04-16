package cn.soybean.system.domain.event.role

import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class RoleDeletedEventBase(
    val aggregateId: String
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROLE_DELETED_V1 = "ROLE_DELETED_V1"
    }
}