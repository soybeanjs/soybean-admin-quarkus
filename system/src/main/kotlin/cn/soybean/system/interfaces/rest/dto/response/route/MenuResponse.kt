package cn.soybean.system.interfaces.rest.dto.response.route

import cn.soybean.domain.enums.DbEnums

data class MenuResponse(
    var id: String? = null,
    var menuName: String? = null,
    var menuType: DbEnums.MenuItemType? = null,
    var order: Int? = null,
    var parentId: String? = null,
    var icon: String? = null,
    var iconType: String? = null,
    var routeName: String? = null,
    var routePath: String? = null,
    var component: String? = null,
    var i18nKey: String? = null,
    var multiTab: Boolean? = null,
    var activeMenu: String? = null,
    var hideInMenu: Boolean? = null,
    var status: DbEnums.Status? = null,
    var roles: List<String>? = null,
    var keepAlive: Boolean? = null,
    var constant: Boolean? = null,
    var href: String? = null,
    var children: List<MenuResponse>? = null
)