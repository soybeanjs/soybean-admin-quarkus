package cn.soybean.system.interfaces.rest.dto.request.auth

import jakarta.validation.constraints.NotBlank
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Login request payload")
class PwdLoginRequest(
    @field:Schema(description = "Tenant Name", example = "exampleTenant", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.tenantName.NotBlank}")
    var tenantName: String,

    @field:Schema(description = "User Login Account", example = "user@example.com", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.Account.NotBlank}")
    var userName: String,

    @field:Schema(description = "User Password", example = "password123", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.password.NotBlank}")
    var password: String
)