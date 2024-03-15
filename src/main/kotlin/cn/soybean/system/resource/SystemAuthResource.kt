package cn.soybean.system.resource

import cn.soybean.framework.common.base.ResponseEntity
import cn.soybean.system.dto.PwdLoginDTO
import cn.soybean.system.service.AuthService
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SystemAuthResource(private val authService: AuthService) {

    @Path("/login")
    @POST
    @WithSession
    fun login(@Valid req: PwdLoginDTO): Uni<Response> = authService.pwdLogin(req)
        .map {
            Response.ok(ResponseEntity.ok(it)).build()
        }
}