package cn.soybean.system.application.command.permission

import cn.soybean.shared.application.command.Command

data class AuthorizeRoleMenuCommand(
    val roleId: String,
    val menuIds: Set<String>
) : Command

data class AuthorizeRoleUserCommand(
    val roleId: String,
    val userIds: Set<String>
) : Command

data class AuthorizeRoleOperationCommand(
    val roleId: String,
    val operationIds: Set<String>
) : Command
