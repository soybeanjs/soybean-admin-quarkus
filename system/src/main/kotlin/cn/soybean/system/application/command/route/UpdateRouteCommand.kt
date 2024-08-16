package cn.soybean.system.application.command.route

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.event.route.RouteCreatedOrUpdatedEventBase
import cn.soybean.shared.application.command.Command
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    RouteCreatedOrUpdatedEventBase::class,
    mappings = [
        Mapping(source = "id", target = "aggregateId")
    ]
)
data class UpdateRouteCommand(
    var id: String,
    var menuName: String,
    var menuType: DbEnums.MenuItemType,
    var order: Int,
    var parentId: String,
    var icon: String? = null,
    var iconType: String? = null,
    var routeName: String,
    var routePath: String,
    var component: String,
    var i18nKey: String,
    var multiTab: Boolean = false,
    var activeMenu: String? = null,
    var hideInMenu: Boolean = false,
    var status: DbEnums.Status,
    var roles: List<String>? = null,
    var keepAlive: Boolean = false,
    var constant: Boolean = false,
    var href: String? = null
) : Command
