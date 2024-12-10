/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest

import cn.soybean.domain.system.entity.SystemMenuEntity
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.command.route.DeleteRouteCommand
import cn.soybean.system.application.query.route.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdAndConstantQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.route.RouteByConstantQuery
import cn.soybean.system.application.query.route.service.RouteQueryService
import cn.soybean.system.application.service.RouteService
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import cn.soybean.system.interfaces.rest.dto.request.route.RouteRequest
import cn.soybean.system.interfaces.rest.dto.request.route.toCreateRouteCommand
import cn.soybean.system.interfaces.rest.dto.request.route.toUpdateRouteCommand
import cn.soybean.system.interfaces.rest.dto.response.route.MenuResponse
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.Authenticated
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.groups.ConvertGroup
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Route", description = "Operations related to routes")
class RouteResource(
    private val routeQueryService: RouteQueryService,
    private val routeService: RouteService,
    private val loginHelper: LoginHelper,
) {
    @Authenticated
    @Path("/getUserRoutes")
    @GET
    @WithSession
    @Operation(summary = "用户路由", description = "获取用户权限路由表")
    fun getUserRoutes(): Uni<ResponseEntity<Map<String, Any>>> =
        routeQueryService.handle(GetRoutesByUserIdQuery(loginHelper.getUserId())).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.list")
    @GET
    @WithSession
    @Operation(summary = "路由列表", description = "获取路由列表")
    fun getMenuList(): Uni<ResponseEntity<List<MenuResponse>>> =
        routeQueryService.handle(ListTreeRoutesByUserIdQuery(loginHelper.getUserId(), loginHelper.getTenantId()))
            .map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建路由", description = "创建路由信息")
    fun createRoute(@Valid @ConvertGroup(to = ValidationGroups.OnCreate::class) @NotNull req: RouteRequest): Uni<ResponseEntity<Boolean>> =
        routeService.createRoute(req.toCreateRouteCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新路由", description = "更新路由信息")
    fun updateRoute(@Valid @ConvertGroup(to = ValidationGroups.OnUpdate::class) @NotNull req: RouteRequest): Uni<ResponseEntity<Boolean>> =
        routeService.updateRoute(req.toUpdateRouteCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除路由", description = "删除路由信息")
    fun deleteRoute(@Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") ids: Set<String>): Uni<ResponseEntity<Boolean>> =
        routeService.deleteRoute(DeleteRouteCommand(ids)).map { (isSuccess, message) ->
            when {
                isSuccess -> ResponseEntity.ok(true)
                else -> ResponseEntity.fail(message, false)
            }
        }

    @Path("/getConstantRoutes")
    @GET
    @WithSession
    @Operation(summary = "常量路由", description = "常量路由列表")
    fun getConstantRoutes(): Uni<ResponseEntity<List<MenuRoute>>> =
        routeQueryService.handle(RouteByConstantQuery()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}api.list_menu_id_by_role_id")
    @GET
    @Path("/listMenuIdByRoleId/{roleId}")
    @WithSession
    @Operation(summary = "根据角色获取已授权路由资源", description = "根据角色获取已授权路由资源")
    fun listMenuIdByRoleId(@PathParam("roleId") roleId: String): Uni<ResponseEntity<List<String>>> =
        SystemMenuEntity.listMenuIdByRoleId(roleId, loginHelper.getUserId(), loginHelper.getTenantId())
            .map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.tree")
    @GET
    @Path("/tree")
    @WithSession
    @Operation(summary = "路由🌲结构", description = "获取路由🌲")
    fun getMenuTree(): Uni<ResponseEntity<List<MenuResponse>>> = routeQueryService.handle(
        ListTreeRoutesByUserIdAndConstantQuery(
            loginHelper.getUserId(),
            loginHelper.getTenantId(),
        ),
    )
        .map { ResponseEntity.ok(it) }
}
