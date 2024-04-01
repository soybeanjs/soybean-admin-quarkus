package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.system.application.command.DeleteRoleCommand
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleCommandHandler : CommandHandler<DeleteRoleCommand, Boolean> {
    override fun handle(command: DeleteRoleCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRoleCommand
}