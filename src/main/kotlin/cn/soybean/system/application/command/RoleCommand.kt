package cn.soybean.system.application.command

import cn.soybean.domain.Command

data class DeleteRoleCommand(val ids: Set<Long>) : Command