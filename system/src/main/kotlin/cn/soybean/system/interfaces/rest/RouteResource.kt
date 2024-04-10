package cn.soybean.system.interfaces.rest

import cn.soybean.application.command.CommandInvoker
import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.enums.DbEnums
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.application.query.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.RouteByIdQuery
import cn.soybean.system.application.query.service.RouteQueryService
import cn.soybean.system.interfaces.rest.dto.request.RouteRequest
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import cn.soybean.system.interfaces.rest.dto.request.toCreateRouteCommand
import cn.soybean.system.interfaces.rest.dto.request.toUpdateRouteCommand
import cn.soybean.system.interfaces.rest.vo.MenuRespVO
import cn.soybean.system.interfaces.rest.vo.MenuRoute
import cn.soybean.system.interfaces.rest.vo.RouteMeta
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.Authenticated
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
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
    private val commandInvoker: CommandInvoker,
    private val loginHelper: LoginHelper
) {

    @Authenticated
    @Path("/getUserRoutes")
    @GET
    @WithSession
    @Operation(summary = "用户路由", description = "获取用户权限路由表")
    fun getUserRoutes(): Uni<ResponseEntity<Map<String, Any>>> =
        routeQueryService.handle(GetRoutesByUserIdQuery(loginHelper.getUserId())).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.list")
    @Path("/getMenuList")
    @GET
    @WithSession
    @Operation(summary = "路由列表", description = "获取路由列表")
    fun getMenuList(): Uni<ResponseEntity<List<MenuRespVO>>> =
        routeQueryService.handle(ListTreeRoutesByUserIdQuery(loginHelper.getUserId())).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建路由", description = "创建路由信息")
    fun createRoute(@Valid @ConvertGroup(to = ValidationGroups.OnCreate::class) @NotNull req: RouteRequest): Uni<ResponseEntity<Boolean>> =
        validateParentMenu(req.parentId, req.id).flatMap {
            commandInvoker.dispatch<CreateRouteCommand, Boolean>(req.toCreateRouteCommand())
                .map { ResponseEntity.ok(it) }
        }

    private fun validateParentMenu(parentId: String?, childId: String?): Uni<Unit> = when (parentId) {
        null, DbConstants.PARENT_ID_ROOT -> Uni.createFrom().item(Unit)
        childId -> Uni.createFrom().failure(ServiceException(ErrorCode.SELF_PARENT_MENU_NOT_ALLOWED))
        else -> routeQueryService.handle(RouteByIdQuery(parentId))
            .onItem().ifNull().failWith(ServiceException(ErrorCode.PARENT_MENU_NOT_FOUND))
            .flatMap { menu ->
                when {
                    menu.menuType != DbEnums.MenuItemType.DIRECTORY -> Uni.createFrom()
                        .failure(ServiceException(ErrorCode.PARENT_MENU_TYPE_INVALID))

                    else -> Uni.createFrom().item(Unit)
                }
            }
    }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新路由", description = "更新路由信息")
    fun updateRoute(@Valid @ConvertGroup(to = ValidationGroups.OnUpdate::class) @NotNull req: RouteRequest): Uni<ResponseEntity<Boolean>> =
        validateParentMenu(req.parentId, req.id).flatMap {
            commandInvoker.dispatch<UpdateRouteCommand, Boolean>(req.toUpdateRouteCommand())
                .map { ResponseEntity.ok(it) }
        }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除路由", description = "删除路由信息")
    fun deleteRoute(@Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") ids: Set<String>): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<cn.soybean.system.application.command.DeleteRouteCommand, Boolean>(
            cn.soybean.system.application.command.DeleteRouteCommand(
                ids
            )
        ).map { ResponseEntity.ok(it) }

    @Path("/getConstantRoutes")
    @GET
    @Operation(summary = "常量路由", description = "固定路由列表,暂未从数据库获取,硬编码")
    fun getConstantRoutes(): Uni<ResponseEntity<List<MenuRoute>>> = uni {
        ResponseEntity.ok(
            listOf(
                MenuRoute(
                    name = "403",
                    path = "/403",
                    component = "layout.blank\$view.403",
                    meta = RouteMeta(
                        constant = true,
                        hideInMenu = true,
                        i18nKey = "route.403",
                        title = "403"
                    )
                ),
                MenuRoute(
                    name = "404",
                    path = "/404",
                    component = "layout.blank\$view.404",
                    meta = RouteMeta(
                        constant = true,
                        hideInMenu = true,
                        i18nKey = "route.404",
                        title = "404"
                    )
                ),
                MenuRoute(
                    name = "500",
                    path = "/500",
                    component = "layout.blank\$view.500",
                    meta = RouteMeta(
                        constant = true,
                        hideInMenu = true,
                        i18nKey = "route.500",
                        title = "500"
                    )
                ),
                MenuRoute(
                    name = "user-center",
                    path = "/user-center",
                    component = "layout.base\$view.user-center",
                    meta = RouteMeta(
                        hideInMenu = true,
                        i18nKey = "route.user-center",
                        title = "user-center"
                    )
                ),
                MenuRoute(
                    name = "login",
                    path = "/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?",
                    component = "layout.blank\$view.login",
                    meta = RouteMeta(
                        constant = true,
                        hideInMenu = true,
                        i18nKey = "route.login",
                        title = "login"
                    )
                )
            )
        )
    }
}