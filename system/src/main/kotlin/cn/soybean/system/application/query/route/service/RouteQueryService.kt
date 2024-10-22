package cn.soybean.system.application.query.route.service

import cn.soybean.domain.system.entity.SystemMenuEntity
import cn.soybean.system.application.query.route.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.route.RouteByConstantQuery
import cn.soybean.system.application.query.route.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.route.RouteByIdQuery
import cn.soybean.system.application.query.route.RouteByTenantIdQuery
import cn.soybean.system.interfaces.rest.dto.response.route.MenuResponse
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import io.smallrye.mutiny.Uni

interface RouteQueryService {
    fun handle(query: GetRoutesByUserIdQuery): Uni<Map<String, Any>>
    fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuResponse>>
    fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity>
    fun handle(query: RouteByIdBuiltInQuery): Uni<Boolean>
    fun handle(query: RouteByConstantQuery): Uni<List<MenuRoute>>
    fun handle(query: RouteByTenantIdQuery): Uni<Set<String>>
}
