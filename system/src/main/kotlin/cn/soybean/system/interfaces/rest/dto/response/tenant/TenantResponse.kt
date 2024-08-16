package cn.soybean.system.interfaces.rest.dto.response.tenant

import cn.soybean.domain.system.enums.DbEnums
import java.time.LocalDateTime

data class TenantResponse(
    val name: String?,
    var contactAccountName: String?,
    var status: DbEnums.Status,
    var website: String?,
    var expireTime: LocalDateTime?,
    var menuIds: Set<String>?,
    var operationIds: Set<String>?
)
