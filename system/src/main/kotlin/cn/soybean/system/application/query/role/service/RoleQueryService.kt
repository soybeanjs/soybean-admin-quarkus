package cn.soybean.system.application.query.role.service

import cn.soybean.domain.system.entity.SystemRoleEntity
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.role.PageRoleQuery
import cn.soybean.system.application.query.role.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.role.RoleByIdQuery
import cn.soybean.system.application.query.role.RoleCodeByUserIdQuery
import cn.soybean.system.application.query.role.RoleExistsQuery
import cn.soybean.system.interfaces.rest.dto.response.role.RoleResponse
import io.smallrye.mutiny.Uni

interface RoleQueryService {
    fun handle(query: PageRoleQuery): Uni<PageResult<RoleResponse>>
    fun handle(query: RoleExistsQuery): Uni<Boolean>
    fun handle(query: RoleByIdBuiltInQuery): Uni<Boolean>
    fun handle(query: RoleByIdQuery): Uni<SystemRoleEntity>
    fun handle(query: RoleCodeByUserIdQuery): Uni<Set<String>>
}
