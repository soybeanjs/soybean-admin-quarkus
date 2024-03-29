package cn.soybean.system.interfaces.dto.query

import cn.soybean.domain.enums.DbEnums
import cn.soybean.interfaces.rest.dto.request.BasePageParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

class RoleReqQuery(
    @field:Parameter(description = "角色名称")
    @field:QueryParam("name")
    var name: String? = null,

    @field:Parameter(description = "角色编码")
    @field:QueryParam("code")
    var code: String? = null,

    @field:Parameter(description = "角色状态")
    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()