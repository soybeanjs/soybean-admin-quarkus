package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.consts.AppConstants
import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.UserAppService
import cn.soybean.system.interfaces.dto.UserReqDTO
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(private val userAppService: UserAppService, private val loginHelper: LoginHelper) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.list")
    @Path("/getUserList")
    @GET
    @WithSession
    fun getUserList(queryParam: UserReqDTO): Uni<Response> =
        userAppService.getUserList(queryParam, loginHelper.getTenantId())
            .map {
                Response.ok(ResponseEntity.ok(it)).build()
            }
}