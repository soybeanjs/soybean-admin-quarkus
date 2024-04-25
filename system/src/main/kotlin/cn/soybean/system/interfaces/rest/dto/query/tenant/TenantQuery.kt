package cn.soybean.system.interfaces.rest.dto.query.tenant

import cn.soybean.domain.enums.DbEnums
import cn.soybean.interfaces.rest.dto.request.BasePageParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

class TenantQuery(
    @field:Parameter(description = "租户名称")
    @field:QueryParam("name")
    var name: String? = null,

    @field:Parameter(description = "租户状态")
    @field:QueryParam("status")
    var status: DbEnums.Status? = null
) : BasePageParam()