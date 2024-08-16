package cn.soybean.system.application.query.role.handler

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.system.entity.SystemRoleEntity
import cn.soybean.domain.system.repository.SystemRoleRepository
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.role.PageRoleQuery
import cn.soybean.system.application.query.role.RoleByIdBuiltInQuery
import cn.soybean.system.application.query.role.RoleByIdQuery
import cn.soybean.system.application.query.role.RoleCodeByUserIdQuery
import cn.soybean.system.application.query.role.RoleExistsQuery
import cn.soybean.system.application.query.role.service.RoleQueryService
import cn.soybean.system.interfaces.rest.dto.response.role.RoleResponse
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

// TODO 临时放置
fun SystemRoleEntity.toRoleResponse(): RoleResponse = RoleResponse(
    id = this.id,
    name = this.name,
    code = this.code,
    order = this.order,
    status = this.status,
    builtIn = this.builtIn,
    dataScope = this.dataScope,
    dataScopeDeptIds = this.dataScopeDeptIds,
    remark = this.remark
)

@ApplicationScoped
class RoleQueryHandler(private val systemRoleRepository: SystemRoleRepository) : RoleQueryService {

    override fun handle(query: PageRoleQuery): Uni<PageResult<RoleResponse>> {
        val (q, params, page) = query
        val panacheQuery = systemRoleRepository.getRoleList(q, Sort.by("order"), params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toRoleResponse() }, page.index + 1, page.size, count)
            }
        }
    }

    override fun handle(query: RoleExistsQuery): Uni<Boolean> =
        systemRoleRepository.existsByCode(query.code, query.tenantId)

    override fun handle(query: RoleByIdBuiltInQuery): Uni<Boolean> =
        systemRoleRepository.getById(query.id, query.tenantId).map { it?.builtIn != false }

    override fun handle(query: RoleByIdQuery): Uni<SystemRoleEntity> =
        systemRoleRepository.getById(query.id, query.tenantId).onItem().transformToUni { role ->
            when {
                role != null -> Uni.createFrom().item(role)
                else -> Uni.createFrom().failure(ServiceException(ErrorCode.ROLE_NOT_FOUND))
            }
        }

    override fun handle(query: RoleCodeByUserIdQuery): Uni<Set<String>> =
        systemRoleRepository.getRoleCodesByUserId(query.userId)
}
