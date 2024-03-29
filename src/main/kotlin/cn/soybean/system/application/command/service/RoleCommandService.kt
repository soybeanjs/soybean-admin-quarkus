package cn.soybean.system.application.command.service

import cn.soybean.system.application.command.DeleteRoleCommand
import io.smallrye.mutiny.Uni

interface RoleCommandService {
    fun handle(command: DeleteRoleCommand): Uni<Boolean>
}