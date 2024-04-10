package cn.soybean.system.interfaces.rest.vo

import cn.soybean.domain.enums.DbEnums

data class RoleRespVO(
    var id: String? = null,
    var name: String? = null,
    var code: String? = null,
    var order: Int? = null,
    var status: DbEnums.Status? = null,
    val builtin: Boolean = false,
    val dataScope: DbEnums.DataPermission? = null,
    val dataScopeDeptIds: Set<String>? = null,
    var remark: String? = null
)