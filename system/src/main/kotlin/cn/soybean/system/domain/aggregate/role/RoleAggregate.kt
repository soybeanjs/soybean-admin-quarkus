package cn.soybean.system.domain.aggregate.role

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.event.role.RoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.role.RoleDeletedEventBase
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RoleAggregate @JsonCreator constructor(@JsonProperty("aggregateId") aggregateId: String) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {

    private var name: String? = null
    private var code: String? = null
    private var order: Int? = null
    private var status: DbEnums.Status? = null
    private var dataScope: DbEnums.DataPermission? = null
    private var dataScopeDeptIds: Set<String>? = null
    private var remark: String? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            RoleCreatedOrUpdatedEventBase.ROLE_CREATED_V1, RoleCreatedOrUpdatedEventBase.ROLE_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    RoleCreatedOrUpdatedEventBase::class.java
                )
            )

            RoleDeletedEventBase.ROLE_DELETED_V1 -> SerializerUtils.deserializeFromJsonBytes(
                eventEntity.data,
                RoleDeletedEventBase::class.java
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: RoleCreatedOrUpdatedEventBase) {
        this.name = event.name
        this.code = event.code
        this.order = event.order
        this.status = event.status
        this.dataScope = event.dataScope
        this.dataScopeDeptIds = event.dataScopeDeptIds
        this.remark = event.remark
    }

    fun createRole(data: RoleCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RoleCreatedOrUpdatedEventBase.ROLE_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateRole(data: RoleCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RoleCreatedOrUpdatedEventBase.ROLE_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun deleteRole(data: RoleDeletedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RoleDeletedEventBase.ROLE_DELETED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "RoleAggregate"
    }
}
