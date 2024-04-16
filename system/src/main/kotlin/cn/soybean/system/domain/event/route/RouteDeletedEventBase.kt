package cn.soybean.system.domain.event.route

import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class RouteDeletedEventBase(
    val aggregateId: String
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROUTE_DELETED_V1 = "ROUTE_DELETED_V1"
    }
}