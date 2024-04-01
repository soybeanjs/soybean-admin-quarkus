package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.system.application.command.DeleteRouteCommand
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteCommandHandler : CommandHandler<DeleteRouteCommand, Boolean> {
    override fun handle(command: DeleteRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRouteCommand
}