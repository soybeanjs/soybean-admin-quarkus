package cn.soybean.system.interfaces.rest.dto.query.role

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.interfaces.rest.dto.request.BasePageParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

class RoleQuery(
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
