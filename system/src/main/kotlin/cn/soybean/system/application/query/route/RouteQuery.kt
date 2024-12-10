/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.route

import cn.soybean.shared.application.query.Query

data class GetRoutesByUserIdQuery(
    val userId: String,
) : Query

data class ListTreeRoutesByUserIdQuery(
    val userId: String,
    val tenantId: String,
) : Query

data class RouteByIdQuery(
    val id: String,
) : Query

data class RouteByIdBuiltInQuery(
    val id: String,
) : Query

data class RouteByConstantQuery(
    val constant: Boolean = true,
) : Query

data class RouteByTenantIdQuery(
    val tenantId: String,
) : Query

data class ListTreeRoutesByUserIdAndConstantQuery(
    val userId: String,
    val tenantId: String,
    val constant: Boolean = false,
) : Query
