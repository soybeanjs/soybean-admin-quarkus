package cn.soybean.system.interfaces.rest

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.domain.entity.SystemApisEntity
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Apis", description = "Operations related to apis")
class ApiResource(private val loginHelper: LoginHelper) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}api.list")
    @GET
    @WithSession
    @Operation(summary = "接口资源列表", description = "获取接口资源列表")
    fun getApiList(): Uni<ResponseEntity<List<SystemApisEntity>>> =
        SystemApisEntity.listApiByUserId(loginHelper.getUserId()).map { ResponseEntity.ok(it) }
}
