/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.user.handler

import cn.soybean.domain.system.entity.SystemUserEntity
import cn.soybean.domain.system.repository.SystemUserRepository
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByAccountQuery
import cn.soybean.system.application.query.user.UserByEmailQuery
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByIdQuery
import cn.soybean.system.application.query.user.UserByPhoneNumberQuery
import cn.soybean.system.application.query.user.UserByTenantIdQuery
import cn.soybean.system.application.query.user.UserByaAccountNameQuery
import cn.soybean.system.application.query.user.service.UserQueryService
import cn.soybean.system.interfaces.rest.dto.response.user.UserResponse
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

fun SystemUserEntity.toUserResponse(): UserResponse = UserResponse(
    id = id,
    accountName = accountName,
    nickName = nickName,
    personalProfile = personalProfile,
    email = email,
    countryCode = countryCode,
    phoneCode = phoneCode,
    phoneNumber = phoneNumber,
    gender = gender,
    avatar = avatar,
    deptId = deptId,
    status = status,
)

@ApplicationScoped
class UserQueryHandler(private val systemUserRepository: SystemUserRepository) : UserQueryService {
    override fun handle(query: PageUserQuery): Uni<PageResult<UserResponse>> {
        val (q, params, page) = query
        val panacheQuery = systemUserRepository.getUserList(q, Sort.by("id"), params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toUserResponse() }, page.index + 1, page.size, count)
            }
        }
    }

    override fun handle(query: UserByIdQuery): Uni<SystemUserEntity?> = systemUserRepository.getById(query.id, query.tenantId)

    override fun handle(query: UserByaAccountNameQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getByAccountName(query.accountName, query.tenantId)

    override fun handle(query: UserByPhoneNumberQuery): Uni<SystemUserEntity?> =
        systemUserRepository.getByPhoneNumber(query.phoneNumber, query.tenantId)

    override fun handle(query: UserByEmailQuery): Uni<SystemUserEntity?> = systemUserRepository.getByEmail(query.email, query.tenantId)

    override fun handle(query: UserByIdBuiltInQuery): Uni<Boolean> =
        systemUserRepository.getById(query.id, query.tenantId).map { it?.builtIn != false }

    override fun handle(query: UserByAccountQuery): Uni<SystemUserEntity> =
        systemUserRepository.findByAccountNameOrEmailOrPhoneNumber(query.accountName, query.tenantId)

    override fun handle(query: UserByTenantIdQuery): Uni<Set<String>> = systemUserRepository.findByTenantId(query.tenantId)
        .map { users -> users.map { it.id }.toSet() }
}
