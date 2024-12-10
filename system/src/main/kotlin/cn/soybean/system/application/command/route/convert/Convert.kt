/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.route.convert

import cn.soybean.domain.system.event.route.RouteCreatedOrUpdatedEventBase
import cn.soybean.system.application.command.route.CreateRouteCommand

fun CreateRouteCommand.convert2RouteCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    createBy: String,
    createAccountName: String,
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
        href = href,
    ).also {
        it.tenantId = tenantId
        it.createBy = createBy
        it.createAccountName = createAccountName
    }
