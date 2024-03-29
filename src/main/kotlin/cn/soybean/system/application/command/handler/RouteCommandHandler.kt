package cn.soybean.system.application.command.handler

import cn.soybean.system.application.command.DeleteRouteCommand
import cn.soybean.system.application.command.service.RouteCommandService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteCommandHandler : RouteCommandService {
    override fun handle(command: DeleteRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }
}