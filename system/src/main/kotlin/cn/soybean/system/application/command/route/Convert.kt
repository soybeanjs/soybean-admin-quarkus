package cn.soybean.system.application.command.route

import cn.soybean.system.domain.event.route.RouteCreatedOrUpdatedEventBase

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

