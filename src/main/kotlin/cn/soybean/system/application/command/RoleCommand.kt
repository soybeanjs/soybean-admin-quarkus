package cn.soybean.system.application.command

import cn.soybean.domain.Command
import cn.soybean.domain.enums.DbEnums

data class CreateRoleCommand(
    var name: String,
    var code: String,
    var order: Int,
    var status: DbEnums.Status,
    var dataScope: DbEnums.DataPermission? = null,
    var dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
) : Command

data class UpdateRoleCommand(
    var id: String,
    var name: String,
    var code: String,
    var order: Int,
    var status: DbEnums.Status,
    var dataScope: DbEnums.DataPermission? = null,
    var dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
) : Command

data class DeleteRoleCommand(val ids: Set<String>) : Command