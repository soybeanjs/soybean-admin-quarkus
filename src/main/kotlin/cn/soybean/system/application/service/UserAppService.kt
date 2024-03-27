package cn.soybean.system.application.service

import cn.soybean.framework.interfaces.dto.PageResult
import cn.soybean.framework.interfaces.dto.QueryBuilder
import cn.soybean.system.domain.entity.toUserRespVO
import cn.soybean.system.domain.service.UserService
import cn.soybean.system.interfaces.dto.query.UserReqQuery
import cn.soybean.system.interfaces.vo.UserRespVO
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserAppService(private val userService: UserService) {

    fun getUserList(queryParam: UserReqQuery, tenantId: Long?): Uni<PageResult<UserRespVO>> =
        tenantId?.let { nonNullTenantId ->
            val queryBuilder = QueryBuilder(nonNullTenantId)
            queryParam.accountName?.let { queryBuilder.addLikeCondition("accountName", it) }
            queryParam.gender?.let { queryBuilder.addCondition("gender", it) }
            queryParam.nickName?.let { queryBuilder.addLikeCondition("nickName", it) }
            queryParam.phoneNumber?.let { queryBuilder.addCondition("phoneNumber", it) }
            queryParam.email?.let { queryBuilder.addCondition("email", it) }
            queryParam.status?.let { queryBuilder.addCondition("status", it) }
            val (query, params) = queryBuilder.buildParameters()

            val page = Page.of(queryParam.getAdjustedPageNo(), queryParam.size)
            val panacheQuery = userService.getUserList(query, Sort.by("id"), params).page(page)
            panacheQuery.list().flatMap { resultList ->
                panacheQuery.count().map { count ->
                    PageResult(resultList.map { it.toUserRespVO() }, queryParam.index, queryParam.size, count)
                }
            }
        } ?: Uni.createFrom().item(PageResult.empty())

    fun deleteUser(id: List<Long>): Uni<Boolean> = TODO()
}