/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.user.service

import cn.soybean.domain.system.entity.SystemUserEntity
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByAccountQuery
import cn.soybean.system.application.query.user.UserByEmailQuery
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByIdQuery
import cn.soybean.system.application.query.user.UserByPhoneNumberQuery
import cn.soybean.system.application.query.user.UserByTenantIdQuery
import cn.soybean.system.application.query.user.UserByaAccountNameQuery
import cn.soybean.system.interfaces.rest.dto.response.user.UserResponse
import io.smallrye.mutiny.Uni

interface UserQueryService {
    fun handle(query: PageUserQuery): Uni<PageResult<UserResponse>>

    fun handle(query: UserByIdQuery): Uni<SystemUserEntity?>

    fun handle(query: UserByaAccountNameQuery): Uni<SystemUserEntity?>

    fun handle(query: UserByPhoneNumberQuery): Uni<SystemUserEntity?>

    fun handle(query: UserByEmailQuery): Uni<SystemUserEntity?>

    fun handle(query: UserByIdBuiltInQuery): Uni<Boolean>

    fun handle(query: UserByAccountQuery): Uni<SystemUserEntity>

    fun handle(query: UserByTenantIdQuery): Uni<Set<String>>
}
