package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.SystemManageService
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.Authenticated
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class RouteResource(private val systemManageService: SystemManageService, private val loginHelper: LoginHelper) {

    @Authenticated
    @Path("/getUserRoutes")
    @GET
    @WithSession
    fun getUserRoutes(): Uni<Response> = systemManageService.getUserRoutes(loginHelper.getUserId())
        .map {
            Response.ok(ResponseEntity.ok(it)).build()
        }
}