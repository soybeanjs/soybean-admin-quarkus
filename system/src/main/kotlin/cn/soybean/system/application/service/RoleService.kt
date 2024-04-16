package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.system.application.command.role.CreateRoleCommand
import cn.soybean.system.application.command.role.DeleteRoleCommand
import cn.soybean.system.application.command.role.UpdateRoleCommand
import cn.soybean.system.application.query.role.RoleById
import cn.soybean.system.application.query.role.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.role.RoleExistsQuery
import cn.soybean.system.application.query.role.service.RoleQueryService
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleService(private val roleQueryService: RoleQueryService, private val commandInvoker: CommandInvoker) {

    fun createRole(command: CreateRoleCommand, tenantId: String): Uni<Pair<Boolean, String>> =
        checkRoleCode(command.code, tenantId).flatMap { (flag, msg) ->
            when {
                flag -> commandInvoker.dispatch<CreateRoleCommand, Boolean>(command).map { Pair(it, "") }
                else -> Uni.createFrom().item(Pair(false, msg))
            }
        }.onFailure().recoverWithItem { _ ->
            Pair(false, "An error occurred during role creation.")
        }

    fun updateRole(command: UpdateRoleCommand, tenantId: String): Uni<Pair<Boolean, String>> = when {
        command.id.isBlank() -> Uni.createFrom().item(Pair(false, "ID cannot be null or blank."))
        else -> checkRoleCodeForUpdate(command.code, tenantId, command.id).flatMap { (flag, msg) ->
            when {
                flag -> roleQueryService.handle(RoleByIdBuiltInQuery(command.id, tenantId)).flatMap { builtin ->
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

    fun deleteRole(command: DeleteRoleCommand, tenantId: String): Uni<Pair<Boolean, String>> =
        Multi.createFrom().iterable(command.ids)
            .onItem().transformToUniAndConcatenate { id ->
                roleQueryService.handle(RoleByIdBuiltInQuery(id, tenantId))
                    .flatMap { isBuiltIn ->
                        when {
                            isBuiltIn -> Uni.createFrom()
                                .item(Pair(false, "Role does not exist or Built-in roles cannot be modified."))

                            else -> Uni.createFrom().nullItem()
                        }
                    }
            }
            .collect().asList()
            .flatMap { results ->
                val errorResult = results.find { !it.first }
                when {
                    errorResult != null -> Uni.createFrom().item(errorResult)
                    else -> commandInvoker.dispatch<DeleteRoleCommand, Boolean>(command).map { Pair(it, "") }
                }
            }.onFailure().recoverWithItem { _ ->
                Pair(false, "An error occurred during role delete.")
            }

    private fun checkRoleCode(code: String, tenantId: String): Uni<Pair<Boolean, String>> = when (code) {
        DbConstants.SUPER_SYSTEM_ROLE_CODE, DbConstants.SUPER_TENANT_ROLE_CODE ->
            Uni.createFrom().item(Pair(false, "Role code usage is not permitted."))

        else -> roleQueryService.handle(RoleExistsQuery(code, tenantId))
            .flatMap { codeExists ->
                when {
                    codeExists -> Uni.createFrom().item(Pair(false, "Role code already exists."))
                    else -> Uni.createFrom().item(Pair(true, ""))
                }
            }
    }

    private fun checkRoleCodeForUpdate(currentCode: String, tenantId: String, id: String): Uni<Pair<Boolean, String>> =
        when (currentCode) {
            DbConstants.SUPER_SYSTEM_ROLE_CODE, DbConstants.SUPER_TENANT_ROLE_CODE ->
                Uni.createFrom().item(Pair(false, "Role code usage is not permitted."))

            else -> roleQueryService.handle(RoleById(id, tenantId))
                .flatMap { role ->
                    when {
                        role.code != currentCode -> Uni.createFrom()
                            .item(Pair(false, "Role code modification is not permitted."))

                        else -> Uni.createFrom().item(Pair(true, ""))
                    }
                }
        }
}