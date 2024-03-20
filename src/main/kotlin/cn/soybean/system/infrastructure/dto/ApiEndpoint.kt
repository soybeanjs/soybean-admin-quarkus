package cn.soybean.system.infrastructure.dto

import cn.soybean.system.domain.entity.SystemApisEntity
import io.mcarle.konvert.api.KonvertTo

@KonvertTo(SystemApisEntity::class)
data class ApiEndpoint(
    val path: String,
    val httpMethod: String,
    val summary: String?,
    val description: String?,
    val permissions: String?,
    val inclusive: Boolean?,
    val operationId: String
)
