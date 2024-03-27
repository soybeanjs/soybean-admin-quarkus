package cn.soybean.system.interfaces.dto.query

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.dto.BasePageParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

class UserReqQuery(
    @field:Parameter(description = "账户名")
    @field:QueryParam("accountName")
    var accountName: String? = null,

    @field:Parameter(description = "性别")
    @field:QueryParam("gender")
    var gender: DbEnums.Gender? = null,

    @field:Parameter(description = "昵称")
    @field:QueryParam("nickName")
    var nickName: String? = null,

    @field:Parameter(description = "手机号")
    @field:QueryParam("phoneNumber")
    var phoneNumber: String? = null,

    @field:Parameter(description = "电子邮箱")
    @field:QueryParam("email")
    var email: String? = null,

    @field:Parameter(description = "用户状态")
    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()