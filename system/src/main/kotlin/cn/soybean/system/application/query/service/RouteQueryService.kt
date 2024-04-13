package cn.soybean.system.application.query.service

import cn.soybean.system.application.query.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.RouteByIdQuery
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.interfaces.rest.vo.MenuRespVO
import io.smallrye.mutiny.Uni

interface RouteQueryService {
    fun handle(query: GetRoutesByUserIdQuery): Uni<Map<String, Any>>
    fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuRespVO>>
    fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity>
    fun handle(query: RouteByIdBuiltInQuery): Uni<Boolean>
}