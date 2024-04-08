package cn.soybean.system.application.query

data class GetRoutesByUserIdQuery(val userId: String)
data class ListTreeRoutesByUserIdQuery(val userId: String)
data class RouteByIdQuery(val id: String)