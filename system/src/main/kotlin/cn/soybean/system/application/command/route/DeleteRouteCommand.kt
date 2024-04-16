package cn.soybean.system.application.command.route

import cn.soybean.shared.application.command.Command

data class DeleteRouteCommand(val ids: Set<String>) : Command