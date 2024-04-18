package cn.soybean.system.application.query.route.service

import cn.soybean.system.application.query.route.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.route.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.route.RouteByIdQuery
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.interfaces.rest.vo.route.MenuRespVO
import io.smallrye.mutiny.Uni

interface RouteQueryService {
    fun handle(query: GetRoutesByUserIdQuery): Uni<Map<String, Any>>
    fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuRespVO>>
    fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity>
    fun handle(query: RouteByIdBuiltInQuery): Uni<Boolean>
}