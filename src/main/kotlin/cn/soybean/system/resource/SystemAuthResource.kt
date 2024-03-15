package cn.soybean.system.resource

import cn.soybean.framework.common.base.ResponseEntity
import cn.soybean.framework.common.utils.LoginHelper
import cn.soybean.system.dto.PwdLoginDTO
import cn.soybean.system.service.AuthService
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.Authenticated
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SystemAuthResource(private val authService: AuthService, private val loginHelper: LoginHelper) {

    @Path("/login")
    @POST
    @WithSession
    fun login(@Valid req: PwdLoginDTO): Uni<Response> = authService.pwdLogin(req)
        .map {
            Response.ok(ResponseEntity.ok(it)).build()
        }

    @Authenticated
    @Path("/getUserInfo")
    @GET
    @WithSession
    fun getUserInfo(): Uni<Response> =
        uni {
            Response.ok(
                ResponseEntity.ok(
                    mapOf(
                        "userId" to loginHelper.getUserId(),
                        "userName" to loginHelper.getAccountName(),
                        "roles" to loginHelper.getRoles()
                    )
                )
            ).build()
        }
}