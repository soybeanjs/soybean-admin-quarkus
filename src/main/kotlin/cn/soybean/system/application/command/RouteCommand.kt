package cn.soybean.system.application.command

import cn.soybean.domain.Command
import cn.soybean.domain.enums.DbEnums

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