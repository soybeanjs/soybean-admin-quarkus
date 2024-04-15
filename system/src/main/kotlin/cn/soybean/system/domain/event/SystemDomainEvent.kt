package cn.soybean.system.domain.event

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class RoleCreatedOrUpdatedEventBase(
    val aggregateId: String,
    val name: String,
    val code: String,
    val order: Int,
    val status: DbEnums.Status,
    val dataScope: DbEnums.DataPermission? = null,
    val dataScopeDeptIds: Set<String>? = null,
    val remark: String? = null
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROLE_CREATED_V1 = "ROLE_CREATED_V1"
        const val ROLE_UPDATED_V1 = "ROLE_UPDATED_V1"
    }
}

data class RoleDeletedEventBase(
    val aggregateId: String
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROLE_DELETED_V1 = "ROLE_DELETED_V1"
    }
}

data class UserCreatedOrUpdatedEventBase(
    val aggregateId: String,
    val accountName: String,
    var accountPassword: String,
    val nickName: String,
    val personalProfile: String? = null,
    val email: String? = null,
    val countryCode: String,
    val phoneCode: String,
    val phoneNumber: String? = null,
    val gender: DbEnums.Gender? = null,
    val avatar: String? = null,
    val deptId: String? = null,
    val status: DbEnums.Status
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val USER_CREATED_V1 = "USER_CREATED_V1"
        const val USER_UPDATED_V1 = "USER_UPDATED_V1"
    }
}

data class RouteCreatedOrUpdatedEventBase(
    val aggregateId: String,
    val menuName: String,
    val menuType: DbEnums.MenuItemType,
    val order: Int,
    val parentId: String,
    val icon: String? = null,
    val iconType: String? = null,
    val routeName: String,
    val routePath: String,
    val component: String,
    val i18nKey: String,
    val multiTab: Boolean? = null,
    val activeMenu: String? = null,
    val hideInMenu: Boolean? = null,
    val status: DbEnums.Status,
    val roles: List<String>? = null,
    val keepAlive: Boolean? = null,
    val constant: Boolean? = null,
    val href: String? = null
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROUTE_CREATED_V1 = "ROUTE_CREATED_V1"
        const val ROUTE_UPDATED_V1 = "ROUTE_UPDATED_V1"
    }
}

data class RouteDeletedEventBase(
    val aggregateId: String
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROUTE_DELETED_V1 = "ROUTE_DELETED_V1"
    }
}