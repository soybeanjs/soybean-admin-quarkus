package cn.soybean.system.application.query.role.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.role.PageRoleQuery
import cn.soybean.system.application.query.role.RoleById
import cn.soybean.system.application.query.role.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.role.RoleExistsQuery
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.smallrye.mutiny.Uni

interface RoleQueryService {
    fun handle(query: PageRoleQuery): Uni<PageResult<RoleRespVO>>
    fun handle(query: RoleExistsQuery): Uni<Boolean>
    fun handle(query: RoleByIdBuiltInQuery): Uni<Boolean>
    fun handle(query: RoleById): Uni<SystemRoleEntity>
}