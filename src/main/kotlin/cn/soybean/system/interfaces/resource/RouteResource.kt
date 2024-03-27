package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.consts.AppConstants
import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.RouteAppService
import cn.soybean.system.interfaces.vo.MenuRoute
import cn.soybean.system.interfaces.vo.RouteMeta
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.Authenticated
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class RouteResource(private val routeAppService: RouteAppService, private val loginHelper: LoginHelper) {

    @Authenticated
    @Path("/getUserRoutes")
    @GET
    @WithSession
    fun getUserRoutes(): Uni<Response> = routeAppService.getUserRoutes(loginHelper.getUserId())
        .map {
            Response.ok(ResponseEntity.ok(it)).build()
        }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}route.list")
    @Path("/getMenuList")
    @GET
    @WithSession
    fun getMenuList(): Uni<Response> = routeAppService.getMenuList(loginHelper.getUserId())
        .map {
            Response.ok(ResponseEntity.ok(it)).build()
        }

    @Path("/getConstantRoutes")
    @GET
    fun getConstantRoutes(): Uni<Response> = uni {
        Response.ok(
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
        ).build()
    }
}