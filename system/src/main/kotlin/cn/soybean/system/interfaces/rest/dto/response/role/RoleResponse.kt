package cn.soybean.system.interfaces.rest.dto.response.role

import cn.soybean.domain.system.enums.DbEnums

data class RoleResponse(
    var id: String? = null,
    var name: String? = null,
    var code: String? = null,
    var order: Int? = null,
    var status: DbEnums.Status? = null,
    val builtIn: Boolean = false,
    val dataScope: DbEnums.DataPermission? = null,
    val dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
)
