package cn.soybean.system.application.command.user

import cn.soybean.shared.application.command.Command

data class DeleteUserCommand(val ids: Set<String>) : Command