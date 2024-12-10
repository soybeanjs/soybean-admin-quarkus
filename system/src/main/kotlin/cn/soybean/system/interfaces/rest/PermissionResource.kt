/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.service.PermissionService
import cn.soybean.system.interfaces.rest.dto.request.permission.AuthorizeRoleMenuRequest
import cn.soybean.system.interfaces.rest.dto.request.permission.AuthorizeRoleOperationRequest
import cn.soybean.system.interfaces.rest.dto.request.permission.AuthorizeUserRoleRequest
import cn.soybean.system.interfaces.rest.dto.request.permission.toAuthorizeRoleMenuCommand
import cn.soybean.system.interfaces.rest.dto.request.permission.toAuthorizeRoleOperationCommand
import cn.soybean.system.interfaces.rest.dto.request.permission.toAuthorizeRoleUserCommand
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/permission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Permissions", description = "Operations related to permissions")
class PermissionResource(private val permissionService: PermissionService, private val loginHelper: LoginHelper) {
    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}permission.auth_role_menu")
    @Path("/authRoleMenu")
    @POST
    @WithTransaction
    @Operation(summary = "角色授权菜单", description = "角色授权菜单资源")
    fun authorizeRoleMenus(@Valid @NotNull req: AuthorizeRoleMenuRequest): Uni<ResponseEntity<Boolean>> =
        permissionService.authorizeRoleMenus(req.toAuthorizeRoleMenuCommand(), loginHelper.getTenantId())
            .map { (isSuccess, message) ->
                when {
                    isSuccess -> ResponseEntity.ok(true)
                    else -> ResponseEntity.fail(message, false)
                }
            }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}permission.auth_user_role")
    @Path("/authUserRole")
    @POST
    @WithTransaction
    @Operation(summary = "用户授权角色", description = "用户授权角色资源")
    fun authorizeUserRoles(@Valid @NotNull req: AuthorizeUserRoleRequest): Uni<ResponseEntity<Boolean>> =
        permissionService.authorizeUserRoles(req.toAuthorizeRoleUserCommand(), loginHelper.getTenantId())
            .map { (isSuccess, message) ->
                when {
                    isSuccess -> ResponseEntity.ok(true)
                    else -> ResponseEntity.fail(message, false)
                }
            }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}permission.auth_role_operation")
    @Path("/authRoleOperation")
    @POST
    @WithTransaction
    @Operation(summary = "角色授权API", description = "角色授权API资源")
    fun authorizeRoleOperations(@Valid @NotNull req: AuthorizeRoleOperationRequest): Uni<ResponseEntity<Boolean>> =
        permissionService.authorizeRoleOperations(req.toAuthorizeRoleOperationCommand(), loginHelper.getTenantId())
            .map { (isSuccess, message) ->
                when {
                    isSuccess -> ResponseEntity.ok(true)
                    else -> ResponseEntity.fail(message, false)
                }
            }
}
