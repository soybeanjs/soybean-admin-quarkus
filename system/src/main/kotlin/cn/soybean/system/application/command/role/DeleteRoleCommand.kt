package cn.soybean.system.application.command.role

import cn.soybean.shared.application.command.Command

data class DeleteRoleCommand(val ids: Set<String>) : Command