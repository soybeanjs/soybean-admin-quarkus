package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateUserCommandHandler : CommandHandler<CreateUserCommand, Boolean> {
    override fun handle(command: CreateUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is CreateUserCommand
}

@ApplicationScoped
class UpdateUserCommandHandler : CommandHandler<UpdateUserCommand, Boolean> {
    override fun handle(command: UpdateUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is UpdateUserCommand
}

@ApplicationScoped
class DeleteUserCommandHandler : CommandHandler<DeleteUserCommand, Boolean> {
    override fun handle(command: DeleteUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteUserCommand
}