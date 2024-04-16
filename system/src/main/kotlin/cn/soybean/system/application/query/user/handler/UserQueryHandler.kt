package cn.soybean.system.application.query.user.handler

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByEmail
import cn.soybean.system.application.query.user.UserById
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByPhoneNumber
import cn.soybean.system.application.query.user.UserByaAccountName
import cn.soybean.system.application.query.user.service.UserQueryService
import cn.soybean.system.domain.entity.SystemUserEntity
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

    override fun handle(query: UserById): Uni<SystemUserEntity?> =
        systemUserRepository.getById(query.id, query.tenantId)

    override fun handle(query: UserByaAccountName): Uni<SystemUserEntity?> =
        systemUserRepository.getByAccountName(query.accountName, query.tenantId)

    override fun handle(query: UserByPhoneNumber): Uni<SystemUserEntity?> =
        systemUserRepository.getByPhoneNumber(query.phoneNumber, query.tenantId)

    override fun handle(query: UserByEmail): Uni<SystemUserEntity?> =
        systemUserRepository.getByEmail(query.email, query.tenantId)

    override fun handle(query: UserByIdBuiltInQuery): Uni<Boolean> =
        systemUserRepository.getById(query.id, query.tenantId).map { it?.builtin ?: true }
}