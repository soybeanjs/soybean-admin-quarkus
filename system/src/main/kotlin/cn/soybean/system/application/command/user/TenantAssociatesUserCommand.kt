package cn.soybean.system.application.command.user

import cn.soybean.shared.application.command.Command

data class TenantAssociatesUserCommand(
    val tenantId: String,
    val contactUserId: String,
    val contactAccountName: String
) : Command
