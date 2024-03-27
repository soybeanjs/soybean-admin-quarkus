package cn.soybean.system.interfaces.dto.request

import jakarta.validation.constraints.NotBlank
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Login request payload")
class PwdLoginRequest(
    @field:Schema(description = "Tenant Name", example = "exampleTenant", required = true)
    @field:NotBlank(message = "租户名称不能为空")
    var tenantName: String,

    @field:Schema(description = "User Login Account", example = "user@example.com", required = true)
    @field:NotBlank(message = "登录账号不能为空")
    var userName: String,

    @field:Schema(description = "User Password", example = "password123", required = true)
    @field:NotBlank(message = "密码不能为空")
    var password: String
)