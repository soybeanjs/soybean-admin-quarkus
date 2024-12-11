/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.response.tenant

import cn.soybean.domain.system.enums.DbEnums
import java.time.LocalDateTime

data class TenantResponse(
    val id: String,
    val name: String?,
    var contactAccountName: String?,
    var status: DbEnums.Status,
    var website: String?,
    var expireTime: LocalDateTime?,
    var menuIds: Set<String>?,
    var operationIds: Set<String>?,
)
