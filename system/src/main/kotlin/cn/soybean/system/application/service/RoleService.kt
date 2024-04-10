package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.DeleteRoleCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.application.query.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.RoleExistsQuery
import cn.soybean.system.application.query.service.RoleQueryService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleService(private val roleQueryService: RoleQueryService, private val commandInvoker: CommandInvoker) {

    fun createRole(command: CreateRoleCommand, tenantId: String): Uni<Pair<Boolean, String>> {
        return checkRoleCode(command.code, tenantId).flatMap { (flag, msg) ->
            when {
                flag -> commandInvoker.dispatch<CreateRoleCommand, Boolean>(command).map { Pair(it, "") }
                else -> Uni.createFrom().item(Pair(false, msg))
            }
        }.onFailure().recoverWithItem { _ ->
            Pair(false, "An error occurred during role creation.")
        }
    }

    fun updateRole(command: UpdateRoleCommand, tenantId: String): Uni<Pair<Boolean, String>> {
        return when {
            command.id.isBlank() -> Uni.createFrom().item(Pair(false, "ID cannot be null or blank."))
            else -> checkRoleCode(command.code, tenantId).flatMap { (flag, msg) ->
                when {
                    flag -> roleQueryService.handle(RoleByIdBuiltInQuery(command.id)).flatMap { builtin ->
                        when {
                            builtin -> Uni.createFrom().item(Pair(false, "Built-in roles cannot be modified."))

                            else -> commandInvoker.dispatch<UpdateRoleCommand, Boolean>(command).map { Pair(it, "") }
                        }
                    }

                    else -> Uni.createFrom().item(Pair(false, msg))
                }
            }.onFailure().recoverWithItem { _ ->
                Pair(false, "An error occurred during role update.")
            }
        }
    }

    fun deleteRole(command: DeleteRoleCommand): Uni<Pair<Boolean, String>> {
        TODO("Not yet implemented")
    }

    private fun checkRoleCode(code: String, tenantId: String): Uni<Pair<Boolean, String>> = when (code) {
        DbConstants.SUPER_ROLE_CODE ->
            Uni.createFrom().item(Pair(false, "Role code usage is not permitted."))

        else -> roleQueryService.handle(RoleExistsQuery(code, tenantId))
            .flatMap { codeExists ->
                when {
                    codeExists -> Uni.createFrom().item(Pair(false, "Role code already exists."))
                    else -> Uni.createFrom().item(Pair(true, ""))
                }
            }
    }
}