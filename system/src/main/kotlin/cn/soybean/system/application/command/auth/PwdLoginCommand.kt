package cn.soybean.system.application.command.auth

data class PwdLoginCommand(
    var tenantName: String,
    var userName: String,
    var password: String
)