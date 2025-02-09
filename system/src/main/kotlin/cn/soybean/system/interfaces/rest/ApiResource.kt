/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest

import cn.soybean.domain.system.entity.SystemApisEntity
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Apis", description = "Operations related to apis")
class ApiResource(
    private val loginHelper: LoginHelper,
) {
    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}api.list")
    @GET
    @WithSession
    @Operation(summary = "接口资源列表", description = "获取接口资源列表")
    fun getApiList(): Uni<ResponseEntity<List<SystemApisEntity>>> =
        SystemApisEntity.listApi(loginHelper.getUserId(), loginHelper.getTenantId()).map { ResponseEntity.ok(it) }

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}api.list_api_operation_id_by_role_id")
    @GET
    @Path("/listApiOperationIdByRoleId/{roleId}")
    @WithSession
    @Operation(summary = "根据角色获取已授权接口资源", description = "根据角色获取已授权接口资源")
    fun listApiOperationIdByRoleId(
        @PathParam("roleId") roleId: String,
    ): Uni<ResponseEntity<List<String>>> =
        SystemApisEntity
            .listApiOperationIdByRoleId(roleId, loginHelper.getUserId(), loginHelper.getTenantId())
            .map { ResponseEntity.ok(it) }
}
