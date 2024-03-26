package cn.soybean.system.interfaces.vo

import cn.soybean.framework.common.consts.enums.DbEnums

data class RoleRespVO(
    var id: Long? = null,
    var name: String? = null,
    var code: String? = null,
    var order: Int? = null,
    var status: DbEnums.Status? = null,
    val builtin: Boolean = false,
    val dataScope: DbEnums.DataPermission? = null,
    val dataScopeDeptIds: Set<Long>? = null,
    var remark: String? = null
)