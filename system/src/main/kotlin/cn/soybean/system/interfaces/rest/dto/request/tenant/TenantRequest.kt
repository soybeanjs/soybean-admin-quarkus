/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.request.tenant

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.system.application.command.tenant.CreateTenantCommand
import cn.soybean.system.application.command.tenant.UpdateTenantCommand
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import java.time.LocalDateTime

@KonvertTo(
    CreateTenantCommand::class,
    options = [
        Konfig(key = "konvert.enforce-not-null", value = "true"),
    ],
)
@KonvertTo(
    UpdateTenantCommand::class,
    options = [
        Konfig(key = "konvert.enforce-not-null", value = "true"),
    ],
)
data class TenantRequest(
    @field:Null(groups = [ValidationGroups.OnCreate::class])
    @field:NotNull(groups = [ValidationGroups.OnUpdate::class])
    var id: String? = null,
    @field:NotBlank
    var name: String? = null,
    @field:NotBlank
    var contactAccountName: String? = null,
    @field:NotNull
    var status: DbEnums.Status = DbEnums.Status.ENABLED,
    var website: String? = null,
    var expireTime: LocalDateTime? = null,
    var menuIds: Set<String>? = null,
    var operationIds: Set<String>? = null,
)
