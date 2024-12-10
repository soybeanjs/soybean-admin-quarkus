/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.system.application.command.route.CreateRouteCommand
import cn.soybean.system.application.command.route.DeleteRouteCommand
import cn.soybean.system.application.command.route.UpdateRouteCommand
import cn.soybean.system.application.query.route.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.route.RouteByIdQuery
import cn.soybean.system.application.query.route.service.RouteQueryService
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteService(private val routeQueryService: RouteQueryService, private val commandInvoker: CommandInvoker) {
    fun createRoute(command: CreateRouteCommand): Uni<Boolean> = validateParentMenu(command.parentId, null).flatMap {
        commandInvoker.dispatch<CreateRouteCommand, Boolean>(
            command,
        ).map { it }
    }

    fun updateRoute(command: UpdateRouteCommand): Uni<Boolean> = validateParentMenu(command.parentId, command.id).flatMap {
        commandInvoker.dispatch<UpdateRouteCommand, Boolean>(
            command,
        ).map { it }
    }

    fun deleteRoute(command: DeleteRouteCommand): Uni<Pair<Boolean, String>> = Multi.createFrom().iterable(command.ids)
        .onItem().transformToUniAndMerge { id ->
            routeQueryService.handle(RouteByIdBuiltInQuery(id))
                .flatMap { isBuiltIn ->
                    when {
                        isBuiltIn ->
                            Uni.createFrom()
                                .item(Pair(false, "Route does not exist or Built-in routes cannot be modified."))

                        else -> Uni.createFrom().nullItem()
                    }
                }
        }
        .collect().asList()
        .flatMap { results ->
            val errorResult = results.find { !it.first }
            errorResult?.let { Uni.createFrom().item(it) } ?: commandInvoker.dispatch<DeleteRouteCommand, Boolean>(
                command,
            ).map { Pair(it, "") }
        }.onFailure().recoverWithItem { _ ->
            Pair(false, "An error occurred during route delete.")
        }

    private fun validateParentMenu(parentId: String?, childId: String?): Uni<Unit> = when (parentId) {
        null, DbConstants.PARENT_ID_ROOT -> Uni.createFrom().item(Unit)
        childId -> Uni.createFrom().failure(ServiceException(ErrorCode.SELF_PARENT_MENU_NOT_ALLOWED))
        else ->
            routeQueryService.handle(RouteByIdQuery(parentId))
                .onItem().ifNull().failWith(ServiceException(ErrorCode.PARENT_MENU_NOT_FOUND))
                .flatMap { menu ->
                    when {
                        menu.menuType != DbEnums.MenuItemType.DIRECTORY ->
                            Uni.createFrom()
                                .failure(ServiceException(ErrorCode.PARENT_MENU_TYPE_INVALID))

                        else -> Uni.createFrom().item(Unit)
                    }
                }
    }
}
