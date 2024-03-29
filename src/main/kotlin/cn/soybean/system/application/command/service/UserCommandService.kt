package cn.soybean.system.application.command.service

import cn.soybean.system.application.command.DeleteUserCommand
import io.smallrye.mutiny.Uni

interface UserCommandService {
    fun handle(command: DeleteUserCommand): Uni<Boolean>
}