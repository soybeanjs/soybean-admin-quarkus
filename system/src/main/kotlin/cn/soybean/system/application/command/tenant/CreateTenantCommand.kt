package cn.soybean.system.application.command.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.application.command.Command
import java.time.LocalDateTime

data class CreateTenantCommand(
    val name: String,
    val contactAccountName: String,
    val status: DbEnums.Status,
    val website: String? = null,
    val expireTime: LocalDateTime,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null
) : Command
