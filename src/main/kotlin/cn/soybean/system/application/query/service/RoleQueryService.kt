package cn.soybean.system.application.query.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageRoleQuery
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.smallrye.mutiny.Uni

interface RoleQueryService {
    fun handle(query: PageRoleQuery): Uni<PageResult<RoleRespVO>>
}