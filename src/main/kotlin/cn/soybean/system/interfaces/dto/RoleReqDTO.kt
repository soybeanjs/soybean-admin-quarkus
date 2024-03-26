package cn.soybean.system.interfaces.dto

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.dto.BasePageParam
import jakarta.ws.rs.QueryParam

class RoleReqDTO(
    @field:QueryParam("name")
    var name: String? = null,

    @field:QueryParam("code")
    var code: String? = null,

    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()