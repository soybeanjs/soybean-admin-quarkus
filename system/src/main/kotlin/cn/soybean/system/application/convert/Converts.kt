/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.convert

import cn.soybean.domain.system.entity.SystemMenuEntity
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import cn.soybean.system.interfaces.rest.dto.response.route.RouteMeta

fun SystemMenuEntity.convertToMenuResponse(): MenuRoute =
    MenuRoute(
        parentId = this.parentId,
        name = this.routeName,
        path = this.routePath,
        component = this.component,
        meta =
            RouteMeta(
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
                multiTab = this.multiTab,
            ),
    )
