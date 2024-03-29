package cn.soybean.system.application.command.service

import cn.soybean.system.application.command.DeleteRouteCommand
import io.smallrye.mutiny.Uni

interface RouteCommandService {
    fun handle(command: DeleteRouteCommand): Uni<Boolean>
}