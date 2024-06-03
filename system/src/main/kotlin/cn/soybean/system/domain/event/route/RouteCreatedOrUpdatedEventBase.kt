package cn.soybean.system.domain.event.route

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

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
    val multiTab: Boolean = false,
    val activeMenu: String? = null,
    val hideInMenu: Boolean = false,
    val status: DbEnums.Status,
    val roles: List<String>? = null,
    val keepAlive: Boolean = false,
    val constant: Boolean = false,
    val href: String? = null
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val ROUTE_CREATED_V1 = "ROUTE_CREATED_V1"
        const val ROUTE_UPDATED_V1 = "ROUTE_UPDATED_V1"
    }
}
