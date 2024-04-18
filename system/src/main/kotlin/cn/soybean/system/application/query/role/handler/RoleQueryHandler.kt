package cn.soybean.system.application.query.role.handler

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.role.PageRoleQuery
import cn.soybean.system.application.query.role.RoleById
import cn.soybean.system.application.query.role.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.role.RoleExistsQuery
import cn.soybean.system.application.query.role.service.RoleQueryService
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.toRoleRespVO
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.interfaces.rest.vo.role.RoleRespVO
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
        systemRoleRepository.getById(query.id, query.tenantId).map { it?.builtin ?: true }

    override fun handle(query: RoleById): Uni<SystemRoleEntity> =
        systemRoleRepository.getById(query.id, query.tenantId).onItem().transformToUni { role ->
            when {
                role != null -> Uni.createFrom().item(role)
                else -> Uni.createFrom().failure(ServiceException(ErrorCode.ROLE_NOT_FOUND))
            }
        }
}