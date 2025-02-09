/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.domain.system.aggregate.tenant.TenantAggregate
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.system.application.command.tenant.CreateTenantCommand
import cn.soybean.system.application.command.tenant.DeleteTenantCommand
import cn.soybean.system.application.command.tenant.UpdateTenantCommand
import cn.soybean.system.application.query.tenant.TenantByIdBuiltInQuery
import cn.soybean.system.application.query.tenant.TenantByIdQuery
import cn.soybean.system.application.query.tenant.TenantByNameExistsQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantService(
    private val tenantQueryService: TenantQueryService,
    private val commandInvoker: CommandInvoker,
) {
    fun createTenant(command: CreateTenantCommand): Uni<Pair<Boolean, String>> =
        checkTenantName(command.name)
            .flatMap { (flag, msg) ->
                when {
                    flag -> commandInvoker.dispatch<CreateTenantCommand, TenantAggregate>(command).map { Pair(true, "") }

                    else -> Uni.createFrom().item(Pair(false, msg))
                }
            }.onFailure()
            .recoverWithItem { _ ->
                Pair(false, "An error occurred during tenant creation.")
            }

    fun updateTenant(command: UpdateTenantCommand): Uni<Pair<Boolean, String>> =
        when {
            command.id.isBlank() -> Uni.createFrom().item(Pair(false, "ID cannot be null or blank."))
            else ->
                checkTenantNameForUpdate(command.name, command.id)
                    .flatMap { (flag, msg) ->
                        when {
                            flag ->
                                tenantQueryService.handle(TenantByIdBuiltInQuery(command.id)).flatMap { builtIn ->
                                    when {
                                        builtIn -> Uni.createFrom().item(Pair(false, "Built-in tenants cannot be modified."))

                                        else -> commandInvoker.dispatch<UpdateTenantCommand, Boolean>(command).map { Pair(it, "") }
                                    }
                                }

                            else -> Uni.createFrom().item(Pair(false, msg))
                        }
                    }.onFailure()
                    .recoverWithItem { _ ->
                        Pair(false, "An error occurred during tenant update.")
                    }
        }

    fun deleteTenant(command: DeleteTenantCommand): Uni<Pair<Boolean, String>> =
        Multi
            .createFrom()
            .iterable(command.ids)
            .onItem()
            .transformToUniAndMerge { id ->
                tenantQueryService
                    .handle(TenantByIdBuiltInQuery(id))
                    .flatMap { isBuiltIn ->
                        when {
                            isBuiltIn ->
                                Uni
                                    .createFrom()
                                    .item(Pair(false, "Tenant does not exist or Built-in tenants cannot be modified."))

                            else -> Uni.createFrom().nullItem()
                        }
                    }
            }.collect()
            .asList()
            .flatMap { results ->
                val errorResult = results.find { !it.first }
                errorResult?.let { Uni.createFrom().item(it) } ?: commandInvoker
                    .dispatch<DeleteTenantCommand, Boolean>(
                        command,
                    ).map { Pair(it, "") }
            }.onFailure()
            .recoverWithItem { _ ->
                Pair(false, "An error occurred during tenant delete.")
            }

    private fun checkTenantName(name: String): Uni<Pair<Boolean, String>> =
        when (name) {
            DbConstants.SUPER_TENANT ->
                Uni.createFrom().item(Pair(false, "Tenant code usage is not permitted."))

            else ->
                tenantQueryService
                    .handle(TenantByNameExistsQuery(name))
                    .flatMap { exist ->
                        when {
                            exist -> Uni.createFrom().item(Pair(false, "Tenant name already exists."))
                            else -> Uni.createFrom().item(Pair(true, ""))
                        }
                    }
        }

    private fun checkTenantNameForUpdate(
        name: String,
        id: String,
    ): Uni<Pair<Boolean, String>> =
        when (name) {
            DbConstants.SUPER_TENANT ->
                Uni.createFrom().item(Pair(false, "Tenant code usage is not permitted."))

            else ->
                tenantQueryService
                    .handle(TenantByIdQuery(id))
                    .flatMap { tenant ->
                        when {
                            tenant.name != name -> Uni.createFrom().item(Pair(false, "Tenant name already exists."))
                            else -> Uni.createFrom().item(Pair(true, ""))
                        }
                    }
        }
}
