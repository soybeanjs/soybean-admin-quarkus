package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.consts.AppConstants
import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.dto.PageResult
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.RoleAppService
import cn.soybean.system.interfaces.dto.query.RoleReqQuery
import cn.soybean.system.interfaces.vo.RoleRespVO
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Roles", description = "Operations related to roles")
class RoleResource(private val roleAppService: RoleAppService, private val loginHelper: LoginHelper) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.list")
    @Path("/getRoleList")
    @GET
    @WithSession
    @Operation(summary = "角色列表", description = "获取角色列表")
    fun getRoleList(@Parameter @BeanParam queryParam: RoleReqQuery): Uni<ResponseEntity<PageResult<RoleRespVO>>> =
        roleAppService.getRoleList(queryParam, loginHelper.getTenantId()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建角色", description = "创建角色信息")
    fun createRole(): Uni<ResponseEntity<Boolean>> = TODO()

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新角色", description = "更新角色信息")
    fun updateRole(): Uni<ResponseEntity<Boolean>> = TODO()

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除角色", description = "删除角色信息")
    fun deleteRole(@Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") id: List<Long>): Uni<ResponseEntity<Boolean>> =
        roleAppService.deleteRole(id).map { ResponseEntity.ok(it) }
}