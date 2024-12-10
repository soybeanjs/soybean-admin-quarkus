/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.command.tenant.DeleteTenantCommand
import cn.soybean.system.application.query.tenant.PageTenantQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import cn.soybean.system.application.service.TenantService
import cn.soybean.system.interfaces.rest.dto.query.tenant.TenantQuery
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import cn.soybean.system.interfaces.rest.dto.request.tenant.TenantRequest
import cn.soybean.system.interfaces.rest.dto.request.tenant.toCreateTenantCommand
import cn.soybean.system.interfaces.rest.dto.request.tenant.toUpdateTenantCommand
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.panache.common.Parameters
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.groups.ConvertGroup
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/tenant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tenants", description = "Operations related to tenants")
class TenantResource(
    private val tenantQueryService: TenantQueryService,
    private val tenantService: TenantService,
    private val loginHelper: LoginHelper,
) {
    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}tenant.list")
    @GET
    @WithSession
    @Operation(summary = "租户列表", description = "获取租户列表")
    fun pageQuery(
        @Parameter @BeanParam queryParam: TenantQuery,
    ): Uni<ResponseEntity<PageResult<TenantResponse>>> {
        var query = ""
        val params = Parameters()

        queryParam.name.takeIf { !it.isNullOrEmpty() }?.let {
            query += " and name like :name"
            params.and("name", "%$it%")
        }

        queryParam.status.takeIf { it != null }?.let {
            query += " and status = status"
            params.and("status", "%$it%")
        }
        return tenantQueryService
            .handle(PageTenantQuery(query, params, queryParam.ofPage()))
            .map { ResponseEntity.ok(it) }
    }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}tenant.create")
    @POST
    @WithTransaction
    @Operation(summary = "创建租户", description = "创建租户信息")
    fun createTenant(
        @Valid @ConvertGroup(to = ValidationGroups.OnCreate::class) @NotNull req: TenantRequest,
    ): Uni<ResponseEntity<Boolean>> =
        tenantService.createTenant(req.toCreateTenantCommand()).map { (isSuccess, message) ->
            when {
                isSuccess -> ResponseEntity.ok(true)
                else -> ResponseEntity.fail(message, false)
            }
        }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}tenant.update")
    @PUT
    @WithTransaction
    @Operation(summary = "更新租户", description = "更新租户信息")
    fun updateTenant(
        @Valid @ConvertGroup(to = ValidationGroups.OnUpdate::class) @NotNull req: TenantRequest,
    ): Uni<ResponseEntity<Boolean>> =
        tenantService.updateTenant(req.toUpdateTenantCommand()).map { (isSuccess, message) ->
            when {
                isSuccess -> ResponseEntity.ok(true)
                else -> ResponseEntity.fail(message, false)
            }
        }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}tenant.delete")
    @DELETE
    @WithTransaction
    @Operation(summary = "删除租户", description = "删除租户信息")
    fun deleteTenant(
        @Valid @NotEmpty(message = "{validation.delete.id.NotEmpty}") ids: Set<String>,
    ): Uni<ResponseEntity<Boolean>> =
        tenantService.deleteTenant(DeleteTenantCommand(ids)).map { (isSuccess, message) ->
            when {
                isSuccess -> ResponseEntity.ok(true)
                else -> ResponseEntity.fail(message, false)
            }
        }
}
