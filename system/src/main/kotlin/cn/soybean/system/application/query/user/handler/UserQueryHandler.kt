package cn.soybean.system.application.query.user.handler

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByAccountQuery
import cn.soybean.system.application.query.user.UserByEmailQuery
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByIdQuery
import cn.soybean.system.application.query.user.UserByPhoneNumberQuery
import cn.soybean.system.application.query.user.UserByaAccountNameQuery
import cn.soybean.system.application.query.user.service.UserQueryService
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.entity.toUserRespVO
import cn.soybean.system.domain.repository.SystemUserRepository
import cn.soybean.system.interfaces.rest.vo.user.UserRespVO
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

    override fun handle(query: UserByIdQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getById(query.id, query.tenantId)

    override fun handle(query: UserByaAccountNameQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getByAccountName(query.accountName, query.tenantId)

    override fun handle(query: UserByPhoneNumberQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getByPhoneNumber(query.phoneNumber, query.tenantId)

    override fun handle(query: UserByEmailQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getByEmail(query.email, query.tenantId)

    override fun handle(query: UserByIdBuiltInQuery): Uni<Boolean> =
        systemUserRepository.getById(query.id, query.tenantId).map { it?.builtin ?: true }

    override fun handle(query: UserByAccountQuery): Uni<SystemUserEntity> =
        systemUserRepository.findByAccountNameOrEmailOrPhoneNumber(query.accountName, query.tenantId)
}