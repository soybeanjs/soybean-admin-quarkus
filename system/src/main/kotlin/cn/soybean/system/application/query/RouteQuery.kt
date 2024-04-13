package cn.soybean.system.application.query

import cn.soybean.shared.application.query.Query

data class GetRoutesByUserIdQuery(val userId: String) : Query
data class ListTreeRoutesByUserIdQuery(val userId: String) : Query
data class RouteByIdQuery(val id: String) : Query
data class RouteByIdBuiltInQuery(val id: String) : Query