package cn.soybean.domain.system.aggregate.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.event.tenant.TenantCreatedEventBase
import cn.soybean.domain.system.event.tenant.TenantDeletedEventBase
import cn.soybean.domain.system.event.tenant.TenantUpdatedEventBase
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.util.SerializerUtils
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class TenantAggregate @JsonCreator constructor(@JsonProperty("aggregateId") aggregateId: String) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {

    var name: String? = null
    var contactUserId: String? = null
    var contactAccountName: String? = null
    var status: DbEnums.Status? = null
    var website: String? = null
    var expireTime: LocalDateTime? = null
    var menuIds: Set<String>? = null
    var operationIds: Set<String>? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            TenantCreatedEventBase.TENANT_CREATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    TenantCreatedEventBase::class.java
                )
            )

            TenantUpdatedEventBase.TENANT_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    TenantUpdatedEventBase::class.java
                )
            )

            TenantDeletedEventBase.TENANT_DELETED_V1 -> SerializerUtils.deserializeFromJsonBytes(
                eventEntity.data,
                TenantDeletedEventBase::class.java
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: TenantCreatedEventBase) {
        this.name = event.name
        this.contactUserId = event.contactUserId
        this.contactAccountName = event.contactAccountName
        this.status = event.status
        this.website = event.website
        this.expireTime = event.expireTime
        this.menuIds = event.menuIds
        this.operationIds = event.operationIds
    }

    private fun handle(event: TenantUpdatedEventBase) {
        this.name = event.name
        this.status = event.status
        this.website = event.website
        this.expireTime = event.expireTime
        this.menuIds = event.menuIds
        this.operationIds = event.operationIds
    }

    fun createTenant(data: TenantCreatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(TenantCreatedEventBase.TENANT_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateTenant(data: TenantUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(TenantUpdatedEventBase.TENANT_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun deleteTenant(data: TenantDeletedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(TenantDeletedEventBase.TENANT_DELETED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "TenantAggregate"
    }
}
