package cn.soybean.domain.system.event.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent
import java.time.LocalDateTime

data class TenantUpdatedEventBase(
    val aggregateId: String,
    val name: String,
    val status: DbEnums.Status,
    val website: String? = null,
    val expireTime: LocalDateTime,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val TENANT_UPDATED_V1 = "TENANT_UPDATED_V1"
    }
}
