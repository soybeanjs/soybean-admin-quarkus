package cn.soybean.system.domain

import cn.soybean.domain.AggregateRoot
import cn.soybean.domain.EventEntity
import cn.soybean.domain.SerializerUtils
import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.application.command.UpdateUserCommand
import cn.soybean.system.domain.event.RoleCreatedOrUpdatedEvent
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEvent
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEvent
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

    override fun whenCondition(eventEntity: EventEntity) {
        when (eventEntity.eventType) {
            RoleCreatedOrUpdatedEvent.ROLE_CREATED_V1, RoleCreatedOrUpdatedEvent.ROLE_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    RoleCreatedOrUpdatedEvent::class.java
                )
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: RoleCreatedOrUpdatedEvent) {
        this.name = event.name
        this.code = event.code
        this.order = event.order
        this.status = event.status
        this.dataScope = event.dataScope
        this.dataScopeDeptIds = event.dataScopeDeptIds
        this.remark = event.remark
    }

    fun createRole(command: CreateRoleCommand) {
        val data = RoleCreatedOrUpdatedEvent(
            aggregateId,
            command.name,
            command.code,
            command.order,
            command.status,
            command.dataScope,
            command.dataScopeDeptIds,
            command.remark
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RoleCreatedOrUpdatedEvent.ROLE_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateRole(command: UpdateRoleCommand) {
        val data = RoleCreatedOrUpdatedEvent(
            aggregateId,
            command.name,
            command.code,
            command.order,
            command.status,
            command.dataScope,
            command.dataScopeDeptIds,
            command.remark
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RoleCreatedOrUpdatedEvent.ROLE_UPDATED_V1, dataBytes, null)
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

    override fun whenCondition(eventEntity: EventEntity) {
        when (eventEntity.eventType) {
            UserCreatedOrUpdatedEvent.USER_CREATED_V1, UserCreatedOrUpdatedEvent.USER_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    UserCreatedOrUpdatedEvent::class.java
                )
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: UserCreatedOrUpdatedEvent) {
        this.accountName = event.accountName
        this.accountPassword = event.accountPassword
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

    fun createUser(command: CreateUserCommand) {
        val data = UserCreatedOrUpdatedEvent(
            aggregateId,
            command.accountName,
            command.accountPassword,
            command.nickName,
            command.personalProfile,
            command.email,
            command.countryCode,
            command.phoneCode,
            command.phoneNumber,
            command.gender,
            command.avatar,
            command.deptId,
            command.status
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEvent.USER_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateUser(command: UpdateUserCommand) {
        val data = UserCreatedOrUpdatedEvent(
            aggregateId,
            command.accountName,
            command.accountPassword,
            command.nickName,
            command.personalProfile,
            command.email,
            command.countryCode,
            command.phoneCode,
            command.phoneNumber,
            command.gender,
            command.avatar,
            command.deptId,
            command.status
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEvent.USER_UPDATED_V1, dataBytes, null)
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

    override fun whenCondition(eventEntity: EventEntity) {
        when (eventEntity.eventType) {
            RouteCreatedOrUpdatedEvent.ROUTE_CREATED_V1, RouteCreatedOrUpdatedEvent.ROUTE_UPDATED_V1 -> handle(
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    RouteCreatedOrUpdatedEvent::class.java
                )
            )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: RouteCreatedOrUpdatedEvent) {
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

    fun createRoute(command: CreateRouteCommand) {
        val data = RouteCreatedOrUpdatedEvent(
            aggregateId,
            command.menuName,
            command.menuType,
            command.order,
            command.parentId,
            command.icon,
            command.iconType,
            command.routeName,
            command.routePath,
            command.component,
            command.i18nKey,
            command.multiTab,
            command.activeMenu,
            command.hideInMenu,
            command.status,
            command.roles,
            command.keepAlive,
            command.constant,
            command.href
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEvent.ROUTE_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateRoute(command: UpdateRouteCommand) {
        val data = RouteCreatedOrUpdatedEvent(
            aggregateId,
            command.menuName,
            command.menuType,
            command.order,
            command.parentId,
            command.icon,
            command.iconType,
            command.routeName,
            command.routePath,
            command.component,
            command.i18nKey,
            command.multiTab,
            command.activeMenu,
            command.hideInMenu,
            command.status,
            command.roles,
            command.keepAlive,
            command.constant,
            command.href
        )

        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEvent.ROUTE_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "RouteAggregate"
    }
}