/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.service

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.config.DbConstants.SUPER_TENANT_ROLE_CODE
import cn.soybean.domain.system.entity.SystemApisEntity
import cn.soybean.domain.system.entity.SystemRoleApiEntity
import cn.soybean.domain.system.entity.SystemRoleMenuEntity
import cn.soybean.domain.system.entity.SystemRoleUserEntity
import cn.soybean.domain.system.repository.SystemRoleApiRepository
import cn.soybean.domain.system.repository.SystemRoleMenuRepository
import cn.soybean.domain.system.repository.SystemRoleUserRepository
import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.application.command.permission.AuthorizeRoleMenuCommand
import cn.soybean.system.application.command.permission.AuthorizeRoleOperationCommand
import cn.soybean.system.application.command.permission.AuthorizeRoleUserCommand
import cn.soybean.system.application.query.role.RoleByIdQuery
import cn.soybean.system.application.query.role.service.RoleQueryService
import cn.soybean.system.application.query.route.RouteByTenantIdQuery
import cn.soybean.system.application.query.route.service.RouteQueryService
import cn.soybean.system.application.query.user.UserByIdQuery
import cn.soybean.system.application.query.user.UserByTenantIdQuery
import cn.soybean.system.application.query.user.service.UserQueryService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PermissionService(
    private val roleQueryService: RoleQueryService,
    private val userQueryService: UserQueryService,
    private val routeQueryService: RouteQueryService,
    private val roleMenuRepository: SystemRoleMenuRepository,
    private val roleApiRepository: SystemRoleApiRepository,
    private val roleUserRepository: SystemRoleUserRepository,
) {
    fun authorizeRoleMenus(command: AuthorizeRoleMenuCommand, tenantId: String): Uni<Pair<Boolean, String>> =
        checkRole(command.roleId, tenantId).flatMap { (flag, msg) ->
            when {
                flag -> processAuthorizeRoleMenus(command.roleId, command.menuIds, tenantId)
                else -> Uni.createFrom().item(Pair(false, msg))
            }
        }

    private fun processAuthorizeRoleMenus(roleId: String, menuIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> =
        routeQueryService.handle(RouteByTenantIdQuery(tenantId)).flatMap { availableMenuIds ->
            val validMenuIds = menuIds.intersect(availableMenuIds)
            if (validMenuIds.isEmpty()) {
                Uni.createFrom().item(Pair(false, "No valid menu IDs to assign."))
            } else {
                roleMenuRepository.delByRoleId(roleId, tenantId)
                    .flatMap { addNewRoleMenus(roleId, validMenuIds, tenantId) }
            }
        }

    private fun addNewRoleMenus(roleId: String, menuIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> {
        val roleMenus =
            menuIds.map { menuId ->
                SystemRoleMenuEntity(roleId = roleId, menuId = menuId, tenantId = tenantId)
            }
        return roleMenuRepository.saveOrUpdateAll(roleMenus)
            .map { Pair(true, "Menu IDs successfully assigned to role.") }
            .onFailure().recoverWithItem { _ -> Pair(false, "Failed to assign menus to role.") }
    }

    private fun addNewRoleUsers(roleId: String, userIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> {
        val roleUsers =
            userIds.map { userId ->
                SystemRoleUserEntity(roleId = roleId, userId = userId, tenantId = tenantId)
            }
        return roleUserRepository.saveOrUpdateAll(roleUsers)
            .map { Pair(true, "User IDs successfully assigned to role.") }
            .onFailure().recoverWithItem { _ -> Pair(false, "Failed to assign users to role.") }
    }

    fun authorizeUserRoles(command: AuthorizeRoleUserCommand, tenantId: String): Uni<Pair<Boolean, String>> =
        checkRole(command.roleId, tenantId).flatMap { (flag, msg) ->
            when {
                flag -> processAuthorizeRoleUsers(command.roleId, command.userIds, tenantId)
                else -> Uni.createFrom().item(Pair(false, msg))
            }
        }

    fun authorizeRoleOperations(command: AuthorizeRoleOperationCommand, tenantId: String): Uni<Pair<Boolean, String>> =
        checkRole(command.roleId, tenantId).flatMap { (flag, msg) ->
            when {
                flag -> processAuthorizeRoleOperations(command.roleId, command.operationIds, tenantId)
                else -> Uni.createFrom().item(Pair(false, msg))
            }
        }

    private fun processAuthorizeRoleUsers(roleId: String, userIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> =
        userQueryService.handle(UserByTenantIdQuery(tenantId)).flatMap { availableUserIds ->
            val validUserIds = userIds.intersect(availableUserIds)
            if (validUserIds.isEmpty()) {
                Uni.createFrom().item(Pair(false, "No valid user IDs to assign."))
            } else {
                roleUserRepository.delByRoleId(roleId, tenantId)
                    .flatMap { addNewRoleUsers(roleId, validUserIds, tenantId) }
            }
        }

    private fun processAuthorizeRoleOperations(roleId: String, operationIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> {
        return apisByTenantId(tenantId).flatMap { availableOperationIds ->
            val validOperationIds = availableOperationIds.intersect(operationIds)
            if (validOperationIds.isEmpty()) {
                Uni.createFrom().item(Pair(false, "No valid operation IDs to assign."))
            } else {
                roleApiRepository.delByRoleId(roleId, tenantId)
                    .flatMap { addNewRoleOperationIds(roleId, validOperationIds, tenantId) }
            }
        }
    }

    private fun addNewRoleOperationIds(roleId: String, operationIds: Set<String>, tenantId: String): Uni<Pair<Boolean, String>> {
        val roleOperationIds =
            operationIds.map { operationId ->
                SystemRoleApiEntity(roleId = roleId, operationId = operationId, tenantId = tenantId)
            }
        return roleApiRepository.saveOrUpdateAll(roleOperationIds)
            .map { Pair(true, "Operation IDs successfully assigned to role.") }
            .onFailure().recoverWithItem { _ -> Pair(false, "Failed to assign operations to role.") }
    }

    private fun apisByTenantId(tenantId: String): Uni<Set<String>> = when (tenantId) {
        DbConstants.SUPER_TENANT ->
            SystemApisEntity.listAll()
                .map { apis -> apis.mapNotNull { it.operationId }.toSet() }

        else ->
            roleApiRepository.findOperationIds(tenantId, SUPER_TENANT_ROLE_CODE)
                .map { apis -> apis.mapNotNull { it.operationId }.toSet() }
    }

    private fun checkRole(roleId: String, tenantId: String): Uni<Pair<Boolean, String>> =
        roleQueryService.handle(RoleByIdQuery(roleId, tenantId))
            .flatMap { role ->
                when {
                    role.code == DbConstants.SUPER_SYSTEM_ROLE_CODE || role.code == SUPER_TENANT_ROLE_CODE ->
                        Uni.createFrom().item(Pair(false, "Role code usage is not permitted."))

                    else -> Uni.createFrom().item(Pair(true, ""))
                }
            }

    private fun checkUser(userId: String, tenantId: String): Uni<Pair<Boolean, String>> =
        userQueryService.handle(UserByIdQuery(userId, tenantId))
            .flatMap { user ->
                when {
                    user == null -> Uni.createFrom().failure(ServiceException(ErrorCode.ACCOUNT_NOT_FOUND))

                    isSuperUser(userId) -> Uni.createFrom().item(Pair(false, "User is not permitted."))

                    else -> Uni.createFrom().item(Pair(true, ""))
                }
            }
}
