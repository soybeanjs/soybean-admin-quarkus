package cn.soybean.system.interfaces.resource

import cn.soybean.framework.common.consts.AppConstants
import cn.soybean.framework.common.util.LoginHelper
import cn.soybean.framework.interfaces.dto.PageResult
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.application.service.UserAppService
import cn.soybean.system.interfaces.dto.query.UserReqQuery
import cn.soybean.system.interfaces.vo.UserRespVO
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
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
class UserResource(private val userAppService: UserAppService, private val loginHelper: LoginHelper) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.list")
    @Path("/getUserList")
    @GET
    @WithSession
    @Operation(summary = "用户列表", description = "获取用户列表")
    fun getUserList(@Parameter @BeanParam queryParam: UserReqQuery): Uni<ResponseEntity<PageResult<UserRespVO>>> =
        userAppService.getUserList(queryParam, loginHelper.getTenantId()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建用户", description = "创建用户信息")
    fun createUser(): Uni<ResponseEntity<Boolean>> = TODO()

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新用户", description = "更新用户信息")
    fun updateUser(): Uni<ResponseEntity<Boolean>> = TODO()

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}user.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除用户", description = "删除用户信息")
    fun deleteUser(): Uni<ResponseEntity<Boolean>> = TODO()
}