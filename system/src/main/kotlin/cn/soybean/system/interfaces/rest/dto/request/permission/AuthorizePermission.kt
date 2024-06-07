package cn.soybean.system.interfaces.rest.dto.request.permission

import cn.soybean.system.application.command.permission.AuthorizeRoleMenuCommand
import cn.soybean.system.application.command.permission.AuthorizeRoleOperationCommand
import cn.soybean.system.application.command.permission.AuthorizeUserRoleCommand
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@KonvertTo(
    AuthorizeRoleMenuCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class AuthorizeRoleMenuRequest(
    @field:NotBlank
    var roleId: String? = null,

    @field:NotNull
    @field:NotEmpty
    var menuIds: Set<String>? = null
)

@KonvertTo(
    AuthorizeUserRoleCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class AuthorizeUserRoleRequest(
    @field:NotBlank
    var userId: String? = null,

    @field:NotNull
    @field:NotEmpty
    var roleIds: Set<String>? = null
)

@KonvertTo(
    AuthorizeRoleOperationCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class AuthorizeRoleOperationRequest(
    @field:NotBlank
    var roleId: String? = null,

    @field:NotNull
    @field:NotEmpty
    var operationIds: Set<String>? = null
)
