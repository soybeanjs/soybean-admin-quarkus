package cn.soybean.system.application.command.route

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command

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
    var multiTab: Boolean = false,
    var activeMenu: String? = null,
    var hideInMenu: Boolean = false,
    var status: DbEnums.Status,
    var roles: List<String>? = null,
    var keepAlive: Boolean = false,
    var constant: Boolean = false,
    var href: String? = null
) : Command
