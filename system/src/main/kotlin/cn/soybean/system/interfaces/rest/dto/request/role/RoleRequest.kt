/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.request.role

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.system.application.command.role.CreateRoleCommand
import cn.soybean.system.application.command.role.UpdateRoleCommand
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null

@KonvertTo(
    CreateRoleCommand::class,
    options = [
        Konfig(key = "konvert.enforce-not-null", value = "true"),
    ],
)
@KonvertTo(
    UpdateRoleCommand::class,
    options = [
        Konfig(key = "konvert.enforce-not-null", value = "true"),
    ],
)
data class RoleRequest(
    @field:Null(groups = [ValidationGroups.OnCreate::class])
    @field:NotNull(groups = [ValidationGroups.OnUpdate::class])
    var id: String? = null,
    @field:NotBlank
    var name: String? = null,
    @field:NotBlank
    var code: String,
    @field:NotNull
    var order: Int? = null,
    @field:NotNull
    var status: DbEnums.Status = DbEnums.Status.ENABLED,
    var dataScope: DbEnums.DataPermission? = null,
    var dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null,
)
