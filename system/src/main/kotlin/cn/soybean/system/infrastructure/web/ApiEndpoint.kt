package cn.soybean.system.infrastructure.web

import cn.soybean.domain.system.entity.SystemApisEntity
import io.mcarle.konvert.api.KonvertTo
import java.time.LocalDateTime

@KonvertTo(SystemApisEntity::class)
data class ApiEndpoint(
    val path: String,
    val httpMethod: String,
    val summary: String?,
    val description: String?,
    val permissions: String?,
    val inclusive: Boolean?,
    val operationId: String,
    var createTime: LocalDateTime? = null
)
