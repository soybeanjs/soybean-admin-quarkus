package cn.soybean.system.application.command

import cn.soybean.domain.Command

data class DeleteRouteCommand(val ids: Set<Long>) : Command