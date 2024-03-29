package cn.soybean.system.application.command.handler

import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.service.UserCommandService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserCommandHandler : UserCommandService {
    override fun handle(command: DeleteUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }
}