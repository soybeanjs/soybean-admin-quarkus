package cn.soybean.system.application.query.handler

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageRoleQuery
import cn.soybean.system.application.query.service.RoleQueryService
import cn.soybean.system.application.service.RoleService
import cn.soybean.system.domain.entity.toRoleRespVO
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleQueryHandler(private val roleService: RoleService) : RoleQueryService {

    override fun handle(query: PageRoleQuery): Uni<PageResult<RoleRespVO>> {
        val (q, params, page) = query
        val panacheQuery = roleService.getRoleList(q, Sort.by("order"), params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toRoleRespVO() }, page.index + 1, page.size, count)
            }
        }
    }
}