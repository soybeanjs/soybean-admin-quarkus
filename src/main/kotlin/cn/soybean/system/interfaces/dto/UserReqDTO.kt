package cn.soybean.system.interfaces.dto

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.dto.BasePageParam
import jakarta.ws.rs.QueryParam

class UserReqDTO(
    @field:QueryParam("accountName")
    var accountName: String? = null,

    @field:QueryParam("gender")
    var gender: DbEnums.Gender? = null,

    @field:QueryParam("nickName")
    var nickName: String? = null,

    @field:QueryParam("phoneNumber")
    var phoneNumber: String? = null,

    @field:QueryParam("email")
    var email: String? = null,

    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()