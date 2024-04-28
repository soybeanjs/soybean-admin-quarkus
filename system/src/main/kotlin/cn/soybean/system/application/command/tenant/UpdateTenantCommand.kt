package cn.soybean.system.application.command.tenant

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command
import cn.soybean.system.domain.event.tenant.TenantUpdatedEventBase
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping
import java.time.LocalDateTime

@KonvertTo(
    TenantUpdatedEventBase::class,
    mappings = [
        Mapping(source = "id", target = "aggregateId")
    ]
)
data class UpdateTenantCommand(
    var id: String,
    val name: String,
    val status: DbEnums.Status,
    val website: String? = null,
    val expireTime: LocalDateTime,
    val menuIds: Set<String>? = null,
    val operationIds: Set<String>? = null
) : Command