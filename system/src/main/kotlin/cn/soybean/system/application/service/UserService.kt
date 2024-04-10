package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserService(private val commandInvoker: CommandInvoker) {

    fun createUser(command: CreateUserCommand): Uni<Boolean> =
        commandInvoker.dispatch<CreateUserCommand, Boolean>(command).map { it }

    fun updateUser(command: UpdateUserCommand): Uni<Boolean> =
        commandInvoker.dispatch<UpdateUserCommand, Boolean>(command).map { it }

    fun deleteUser(command: DeleteUserCommand): Uni<Boolean> =
        commandInvoker.dispatch<DeleteUserCommand, Boolean>(command).map { it }
}