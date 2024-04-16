package cn.soybean.system.application.command.role

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command
import cn.soybean.system.domain.event.role.RoleCreatedOrUpdatedEventBase
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    RoleCreatedOrUpdatedEventBase::class,
    mappings = [
        Mapping(source = "id", target = "aggregateId")
    ]
)
data class UpdateRoleCommand(
    var id: String,
    var name: String,
    var code: String,
    var order: Int,
    var status: DbEnums.Status,
    var dataScope: DbEnums.DataPermission? = null,
    var dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
) : Command

