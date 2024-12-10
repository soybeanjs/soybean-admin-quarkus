/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.request.auth

import cn.soybean.system.application.command.auth.PwdLoginCommand
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import org.eclipse.microprofile.openapi.annotations.media.Schema

@KonvertTo(PwdLoginCommand::class)
@Schema(description = "Login request payload")
class PwdLoginRequest(
    @field:Schema(description = "Tenant Name", example = "exampleTenant", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.tenantName.NotBlank}")
    var tenantName: String,
    @field:Schema(description = "User Login Account", example = "user@example.com", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.Account.NotBlank}")
    var userName: String,
    @field:Schema(description = "User Password", example = "password123", required = true)
    @field:NotBlank(message = "{validation.PwdLoginRequest.password.NotBlank}")
    var password: String,
)
