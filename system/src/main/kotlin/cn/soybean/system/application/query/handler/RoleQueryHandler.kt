package cn.soybean.system.application.query.handler

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageRoleQuery
import cn.soybean.system.application.query.RoleById
import cn.soybean.system.application.query.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.RoleExistsQuery
import cn.soybean.system.application.query.service.RoleQueryService
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.toRoleRespVO
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleQueryHandler(private val systemRoleRepository: SystemRoleRepository) : RoleQueryService {

    override fun handle(query: PageRoleQuery): Uni<PageResult<RoleRespVO>> {
        val (q, params, page) = query
        val panacheQuery = systemRoleRepository.getRoleList(q, Sort.by("order"), params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toRoleRespVO() }, page.index + 1, page.size, count)
            }
        }
    }

    override fun handle(query: RoleExistsQuery): Uni<Boolean> =
        systemRoleRepository.existsByCode(query.code, query.tenantId)

    override fun handle(query: RoleByIdBuiltInQuery): Uni<Boolean> =
        query.id?.let { systemRoleRepository.getById(query.id).map { it.builtin } } ?: Uni.createFrom().item(false)

    override fun handle(query: RoleById): Uni<SystemRoleEntity> = systemRoleRepository.getById(query.id)
}