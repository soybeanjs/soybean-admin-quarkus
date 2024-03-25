package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.RoleAppService
import cn.soybean.system.interfaces.dto.RoleReqDTO
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class RoleResource(private val roleAppService: RoleAppService, private val loginHelper: LoginHelper) {

    //    @PermissionsAllowed("action:role@getRoleList")
    @Path("/getRoleList")
    @GET
    @WithSession
    fun getRoleList(queryParam: RoleReqDTO): Uni<Response> =
        roleAppService.getRoleList(queryParam, loginHelper.getTenantId())
            .map {
                Response.ok(ResponseEntity.ok(it)).build()
            }
}