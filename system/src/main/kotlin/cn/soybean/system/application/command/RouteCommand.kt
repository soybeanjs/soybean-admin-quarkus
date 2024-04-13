package cn.soybean.system.application.command

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEventBase
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

data class CreateRouteCommand(
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
    var multiTab: Boolean? = null,
    var activeMenu: String? = null,
    var hideInMenu: Boolean? = null,
    var status: DbEnums.Status,
    var roles: List<String>? = null,
    var keepAlive: Boolean? = null,
    var constant: Boolean? = null,
    var href: String? = null
) : Command

fun CreateRouteCommand.toRouteCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    userId: String,
    accountName: String
): RouteCreatedOrUpdatedEventBase =
    RouteCreatedOrUpdatedEventBase(
        aggregateId = id,
        menuName = menuName,
        menuType = menuType,
        order = order,
        parentId = parentId,
        icon = icon,
        iconType = iconType,
        routeName = routeName,
        routePath = routePath,
        component = component,
        i18nKey = i18nKey,
        multiTab = multiTab,
        activeMenu = activeMenu,
        hideInMenu = hideInMenu,
        status = status,
        roles = roles,
        keepAlive = keepAlive,
        constant = constant,
        href = href
    ).also {
        it.tenantId = tenantId
        it.createBy = userId
        it.createAccountName = accountName
    }

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
    var multiTab: Boolean? = null,
    var activeMenu: String? = null,
    var hideInMenu: Boolean? = null,
    var status: DbEnums.Status,
    var roles: List<String>? = null,
    var keepAlive: Boolean? = null,
    var constant: Boolean? = null,
    var href: String? = null
) : Command

data class DeleteRouteCommand(val ids: Set<String>) : Command