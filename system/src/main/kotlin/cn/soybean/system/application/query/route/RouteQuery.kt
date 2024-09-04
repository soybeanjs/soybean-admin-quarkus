package cn.soybean.system.application.query.route

import cn.soybean.shared.application.query.Query

data class GetRoutesByUserIdQuery(val userId: String) : Query
data class ListTreeRoutesByUserIdQuery(val userId: String, val tenantId: String) : Query
data class RouteByIdQuery(val id: String) : Query
data class RouteByIdBuiltInQuery(val id: String) : Query
data class RouteByConstantQuery(val constant: Boolean = true) : Query
data class RouteByTenantIdQuery(val tenantId: String) : Query
