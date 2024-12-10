/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.role

import cn.soybean.shared.application.query.Query
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters

data class PageRoleQuery(
    val query: String,
    val params: Parameters,
    val page: Page,
) : Query

data class RoleExistsQuery(
    val code: String,
    val tenantId: String,
) : Query

data class RoleByIdBuiltInQuery(
    val id: String,
    val tenantId: String,
) : Query

data class RoleByIdQuery(
    val id: String,
    val tenantId: String,
) : Query

data class RoleCodeByUserIdQuery(
    val userId: String,
) : Query
