package cn.soybean.system.domain.event.tenant

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent
import java.time.LocalDateTime

data class TenantCreatedEventBase(
    val aggregateId: String,
    val name: String,
    val contactUserId: String,
    val contactAccountName: String,
    val status: DbEnums.Status,
    val website: String? = null,
    val expireTime: LocalDateTime,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val TENANT_CREATED_V1 = "TENANT_CREATED_V1"
    }
}