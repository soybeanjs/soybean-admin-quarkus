package cn.soybean.system.application.service

import cn.soybean.infrastructure.persistence.QueryBuilder
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.domain.entity.toRoleRespVO
import cn.soybean.system.domain.service.RoleService
import cn.soybean.system.interfaces.dto.query.RoleReqQuery
import cn.soybean.system.interfaces.vo.RoleRespVO
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleAppService(private val roleService: RoleService) {

    fun getRoleList(queryParam: RoleReqQuery, tenantId: Long?): Uni<PageResult<RoleRespVO>> =
        tenantId?.let { nonNullTenantId ->
            val queryBuilder = QueryBuilder(nonNullTenantId)
            queryParam.name?.let { queryBuilder.addLikeCondition("name", it) }
            queryParam.code?.let { queryBuilder.addLikeCondition("code", it) }
            queryParam.status?.let { queryBuilder.addCondition("status", it) }
            val (query, params) = queryBuilder.buildParameters()

            val page = Page.of(queryParam.getAdjustedPageNo(), queryParam.size)
            val panacheQuery = roleService.getRoleList(query, Sort.by("order"), params).page(page)
            panacheQuery.list().flatMap { resultList ->
                panacheQuery.count().map { count ->
                    PageResult(resultList.map { it.toRoleRespVO() }, queryParam.index, queryParam.size, count)
                }
            }
        } ?: Uni.createFrom().item(PageResult.empty())

    fun deleteRole(id: List<Long>): Uni<Boolean> = TODO()
}