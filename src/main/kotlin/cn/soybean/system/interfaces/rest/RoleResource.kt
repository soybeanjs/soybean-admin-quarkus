package cn.soybean.system.interfaces.rest

import cn.soybean.domain.CommandInvoker
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.persistence.QueryBuilder
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.DeleteRoleCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.application.query.PageRoleQuery
import cn.soybean.system.application.query.service.RoleQueryService
import cn.soybean.system.interfaces.rest.dto.query.RoleQuery
import cn.soybean.system.interfaces.rest.dto.request.RoleRequest
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import cn.soybean.system.interfaces.rest.dto.request.toCreateRoleCommand
import cn.soybean.system.interfaces.rest.dto.request.toUpdateRoleCommand
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.groups.ConvertGroup
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
class RoleResource(
    private val roleQueryService: RoleQueryService,
    private val commandInvoker: CommandInvoker,
    private val loginHelper: LoginHelper
) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.list")
    @Path("/getRoleList")
    @GET
    @WithSession
    @Operation(summary = "角色列表", description = "获取角色列表")
    fun getRoleList(@Parameter @BeanParam queryParam: RoleQuery): Uni<ResponseEntity<PageResult<RoleRespVO>>> {
        val queryBuilder = QueryBuilder(loginHelper.getTenantId())
        queryParam.name?.let { queryBuilder.addLikeCondition("name", it) }
        queryParam.code?.let { queryBuilder.addLikeCondition("code", it) }
        queryParam.status?.let { queryBuilder.addCondition("status", it) }
        val (query, params) = queryBuilder.buildParameters()
        return roleQueryService.handle(PageRoleQuery(query, params, queryParam.ofPage()))
            .map { ResponseEntity.ok(it) }
    }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建角色", description = "创建角色信息")
    fun createRole(@Valid @ConvertGroup(to = ValidationGroups.OnCreate::class) @NotNull req: RoleRequest): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<CreateRoleCommand, Boolean>(req.toCreateRoleCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新角色", description = "更新角色信息")
    fun updateRole(@Valid @ConvertGroup(to = ValidationGroups.OnUpdate::class) @NotNull req: RoleRequest): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<UpdateRoleCommand, Boolean>(req.toUpdateRoleCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}role.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除角色", description = "删除角色信息")
    fun deleteRole(@Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") id: Set<Long>): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<DeleteRoleCommand, Boolean>(DeleteRoleCommand(id)).map { ResponseEntity.ok(it) }
}