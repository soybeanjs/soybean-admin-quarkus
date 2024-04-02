package cn.soybean.system.interfaces.rest

import cn.soybean.domain.CommandInvoker
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.persistence.QueryBuilder
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import cn.soybean.system.application.query.PageUserQuery
import cn.soybean.system.application.query.service.UserQueryService
import cn.soybean.system.interfaces.rest.dto.query.UserQuery
import cn.soybean.system.interfaces.rest.dto.request.UserRequest
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import cn.soybean.system.interfaces.rest.dto.request.toCreateUserCommand
import cn.soybean.system.interfaces.rest.dto.request.toUpdateUserCommand
import cn.soybean.system.interfaces.rest.vo.UserRespVO
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

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "Operations related to users")
class UserResource(
    private val userQueryService: UserQueryService,
    private val commandInvoker: CommandInvoker,
    private val loginHelper: LoginHelper
) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.list")
    @Path("/getUserList")
    @GET
    @WithSession
    @Operation(summary = "用户列表", description = "获取用户列表")
    fun getUserList(@Parameter @BeanParam queryParam: UserQuery): Uni<ResponseEntity<PageResult<UserRespVO>>> {
        val queryBuilder = QueryBuilder(loginHelper.getTenantId())
        queryParam.accountName?.let { queryBuilder.addLikeCondition("accountName", it) }
        queryParam.gender?.let { queryBuilder.addCondition("gender", it) }
        queryParam.nickName?.let { queryBuilder.addLikeCondition("nickName", it) }
        queryParam.phoneNumber?.let { queryBuilder.addCondition("phoneNumber", it) }
        queryParam.email?.let { queryBuilder.addCondition("email", it) }
        queryParam.status?.let { queryBuilder.addCondition("status", it) }
        val (query, params) = queryBuilder.buildParameters()
        return userQueryService.handle(PageUserQuery(query, params, queryParam.ofPage()))
            .map { ResponseEntity.ok(it) }
    }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建用户", description = "创建用户信息")
    fun createUser(@Valid @ConvertGroup(to = ValidationGroups.OnCreate::class) @NotNull req: UserRequest): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<CreateUserCommand, Boolean>(req.toCreateUserCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新用户", description = "更新用户信息")
    fun updateUser(@Valid @ConvertGroup(to = ValidationGroups.OnUpdate::class) @NotNull req: UserRequest): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<UpdateUserCommand, Boolean>(req.toUpdateUserCommand()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除用户", description = "删除用户信息")
    fun deleteUser(@Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") id: Set<Long>): Uni<ResponseEntity<Boolean>> =
        commandInvoker.dispatch<DeleteUserCommand, Boolean>(DeleteUserCommand(id)).map { ResponseEntity.ok(it) }
}