package cn.soybean.system.application.convert

import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import cn.soybean.system.interfaces.rest.dto.response.route.RouteMeta

fun SystemMenuEntity.convertToMenuResponse(): MenuRoute = MenuRoute(
    parentId = this.parentId,
    name = this.routeName,
    path = this.routePath,
    component = this.component,
    meta = RouteMeta(
        title = this.routeName,
        i18nKey = this.i18nKey,
        roles = this.roles,
        keepAlive = this.keepAlive,
        constant = this.constant,
        icon = this.icon,
        order = this.order,
        href = this.href,
        hideInMenu = this.hideInMenu,
        activeMenu = this.activeMenu,
        multiTab = this.multiTab
    )
)