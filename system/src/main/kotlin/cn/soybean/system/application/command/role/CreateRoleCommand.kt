package cn.soybean.system.application.command.role

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command

data class CreateRoleCommand(
    var name: String,
    var code: String,
    var order: Int,
    var status: DbEnums.Status,
    var dataScope: DbEnums.DataPermission? = null,
    var dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
) : Command