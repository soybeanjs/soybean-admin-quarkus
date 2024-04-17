package cn.soybean.system.domain.event.user

import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class UserDeletedEventBase(
    val aggregateId: String
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val USER_DELETED_V1 = "USER_DELETED_V1"
    }
}
