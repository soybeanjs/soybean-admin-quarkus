package cn.soybean.system.application.query.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageRoleQuery
import cn.soybean.system.application.query.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.RoleExistsQuery
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.smallrye.mutiny.Uni

interface RoleQueryService {
    fun handle(query: PageRoleQuery): Uni<PageResult<RoleRespVO>>
    fun handle(query: RoleExistsQuery): Uni<Boolean>
    fun handle(query: RoleByIdBuiltInQuery): Uni<Boolean>
}