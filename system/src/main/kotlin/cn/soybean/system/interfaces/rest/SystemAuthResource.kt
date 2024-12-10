/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.service.AuthService
import cn.soybean.system.interfaces.rest.dto.request.auth.PwdLoginRequest
import cn.soybean.system.interfaces.rest.dto.request.auth.toPwdLoginCommand
import cn.soybean.system.interfaces.rest.dto.response.auth.LoginResponse
import cn.soybean.system.interfaces.rest.dto.response.user.UserInfoResponse
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.security.Authenticated
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Auth", description = "Operations related to auths")
class SystemAuthResource(
    private val authService: AuthService,
    private val loginHelper: LoginHelper,
) {
    @Path("/login")
    @POST
    @WithSession
    @Operation(summary = "账号密码登录", description = "PC端后台管理系统账号密码登录")
    fun login(
        @RequestBody(description = "PC端后台管理系统登录请求体") @Valid @NotNull req: PwdLoginRequest,
    ): Uni<ResponseEntity<LoginResponse>> = authService.pwdLogin(req.toPwdLoginCommand()).map { ResponseEntity.ok(it) }

    @Authenticated
    @Path("/getUserInfo")
    @GET
    @WithSession
    @Operation(summary = "用户信息", description = "获取用户信息")
    fun getUserInfo(): Uni<ResponseEntity<UserInfoResponse>> =
        uni {
            ResponseEntity.ok(
                UserInfoResponse(
                    userId = loginHelper.getUserId(),
                    userName = loginHelper.getAccountName(),
                    roles = loginHelper.getRoles(),
                ),
            )
        }
}
