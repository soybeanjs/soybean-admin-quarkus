package cn.soybean.system.application.command

import cn.soybean.domain.Command

data class DeleteUserCommand(val ids: Set<Long>) : Command