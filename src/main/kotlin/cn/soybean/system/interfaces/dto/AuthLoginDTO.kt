package cn.soybean.system.interfaces.dto

import jakarta.validation.constraints.NotBlank

class PwdLoginDTO(
    @field:NotBlank(message = "租户名称不能为空")
    var tenantName: String,

    @field:NotBlank(message = "登录账号不能为空")
    var userName: String,

    @field:NotBlank(message = "密码不能为空")
    var password: String
)