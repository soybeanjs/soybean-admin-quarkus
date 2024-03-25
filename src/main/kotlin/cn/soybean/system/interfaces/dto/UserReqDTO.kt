package cn.soybean.system.interfaces.dto

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.dto.BasePageParam
import jakarta.ws.rs.QueryParam

class UserReqDTO(
    @field:QueryParam("userName")
    var accountName: String? = null,

    @field:QueryParam("userGender")
    var gender: DbEnums.Gender? = null,

    @field:QueryParam("nickName")
    var nickName: String? = null,

    @field:QueryParam("userPhone")
    var phoneNumber: String? = null,

    @field:QueryParam("userEmail")
    var email: String? = null,

    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()