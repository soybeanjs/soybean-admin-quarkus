package cn.soybean.system.application.command.handler

import cn.soybean.system.application.command.DeleteRoleCommand
import cn.soybean.system.application.command.service.RoleCommandService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleCommandHandler : RoleCommandService {
    override fun handle(command: DeleteRoleCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }
}