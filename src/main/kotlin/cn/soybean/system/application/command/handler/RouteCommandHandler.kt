package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.DeleteRouteCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRouteCommandHandler : CommandHandler<CreateRouteCommand, Boolean> {
    override fun handle(command: CreateRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is CreateRouteCommand
}

@ApplicationScoped
class UpdateRouteCommandHandler : CommandHandler<UpdateRouteCommand, Boolean> {
    override fun handle(command: UpdateRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is UpdateRouteCommand
}

@ApplicationScoped
class RouteCommandHandler : CommandHandler<DeleteRouteCommand, Boolean> {
    override fun handle(command: DeleteRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRouteCommand
}