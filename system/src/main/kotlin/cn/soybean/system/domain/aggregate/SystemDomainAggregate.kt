package cn.soybean.system.domain.aggregate

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.event.RoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.RoleDeletedEventBase
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.RouteDeletedEventBase
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEventBase
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.elytron.security.common.BcryptUtil

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

class UserAggregate @JsonCreator constructor(@JsonProperty("aggregateId") aggregateId: String) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {

    private var accountName: String? = null
    private var accountPassword: String? = null
    private var nickName: String? = null
    private var personalProfile: String? = null
    private var email: String? = null
    private var countryCode: String? = null
    private var phoneCode: String? = null
    private var phoneNumber: String? = null
    private var gender: DbEnums.Gender? = null
    private var avatar: String? = null
    private var deptId: String? = null
    private var status: DbEnums.Status? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            UserCreatedOrUpdatedEventBase.USER_CREATED_V1, UserCreatedOrUpdatedEventBase.USER_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    UserCreatedOrUpdatedEventBase::class.java
                )
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: UserCreatedOrUpdatedEventBase) {
        this.accountName = event.accountName
        this.accountPassword = bcryptHashPassword(event.accountPassword)
        this.nickName = event.nickName
        this.personalProfile = event.personalProfile
        this.email = event.email
        this.countryCode = event.countryCode
        this.phoneCode = event.phoneCode
        this.phoneNumber = event.phoneNumber
        this.gender = event.gender
        this.avatar = event.avatar
        this.deptId = event.deptId
        this.status = event.status
    }

    private fun bcryptHashPassword(password: String) = BcryptUtil.bcryptHash(password)

    fun createUser(data: UserCreatedOrUpdatedEventBase) {
        data.accountPassword = bcryptHashPassword(data.accountPassword)
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEventBase.USER_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateUser(data: UserCreatedOrUpdatedEventBase) {
        data.accountPassword = bcryptHashPassword(data.accountPassword)
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEventBase.USER_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "UserAggregate"
    }
}

class RouteAggregate @JsonCreator constructor(@JsonProperty("aggregateId") aggregateId: String) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {

    private var menuName: String? = null
    private var menuType: DbEnums.MenuItemType? = null
    private var order: Int? = null
    private var parentId: String? = null
    private var icon: String? = null
    private var iconType: String? = null
    private var routeName: String? = null
    private var routePath: String? = null
    private var component: String? = null
    private var i18nKey: String? = null
    private var multiTab: Boolean? = null
    private var activeMenu: String? = null
    private var hideInMenu: Boolean? = null
    private var status: DbEnums.Status? = null
    private var roles: List<String>? = null
    private var keepAlive: Boolean? = null
    private var constant: Boolean? = null
    private var href: String? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            RouteCreatedOrUpdatedEventBase.ROUTE_CREATED_V1, RouteCreatedOrUpdatedEventBase.ROUTE_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    RouteCreatedOrUpdatedEventBase::class.java
                )
            )

            RouteDeletedEventBase.ROUTE_DELETED_V1 -> SerializerUtils.deserializeFromJsonBytes(
                eventEntity.data,
                RouteDeletedEventBase::class.java
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: RouteCreatedOrUpdatedEventBase) {
        this.menuName = event.menuName
        this.menuType = event.menuType
        this.order = event.order
        this.parentId = event.parentId
        this.icon = event.icon
        this.iconType = event.iconType
        this.routeName = event.routeName
        this.routePath = event.routePath
        this.component = event.component
        this.i18nKey = event.i18nKey
        this.multiTab = event.multiTab
        this.activeMenu = event.activeMenu
        this.hideInMenu = event.hideInMenu
        this.status = event.status
        this.roles = event.roles
        this.keepAlive = event.keepAlive
        this.constant = event.constant
        this.href = event.href
    }

    fun createRoute(data: RouteCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEventBase.ROUTE_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateRoute(data: RouteCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEventBase.ROUTE_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun deleteRoute(data: RouteDeletedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteDeletedEventBase.ROUTE_DELETED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "RouteAggregate"
    }
}