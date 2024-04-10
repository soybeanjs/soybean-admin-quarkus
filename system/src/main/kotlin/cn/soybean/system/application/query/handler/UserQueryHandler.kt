package cn.soybean.system.application.query.handler

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageUserQuery
import cn.soybean.system.application.query.service.UserQueryService
import cn.soybean.system.domain.entity.toUserRespVO
import cn.soybean.system.domain.repository.SystemUserRepository
import cn.soybean.system.interfaces.rest.vo.UserRespVO
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserQueryHandler(private val systemUserRepository: SystemUserRepository) : UserQueryService {

    override fun handle(query: PageUserQuery): Uni<PageResult<UserRespVO>> {
        val (q, params, page) = query
        val panacheQuery = systemUserRepository.getUserList(q, Sort.by("id"), params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toUserRespVO() }, page.index + 1, page.size, count)
            }
        }
    }
}