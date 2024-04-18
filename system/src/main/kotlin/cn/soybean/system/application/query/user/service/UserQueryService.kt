package cn.soybean.system.application.query.user.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByAccountQuery
import cn.soybean.system.application.query.user.UserByEmailQuery
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByIdQuery
import cn.soybean.system.application.query.user.UserByPhoneNumberQuery
import cn.soybean.system.application.query.user.UserByaAccountNameQuery
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.interfaces.rest.vo.user.UserRespVO
import io.smallrye.mutiny.Uni

interface UserQueryService {
    fun handle(query: PageUserQuery): Uni<PageResult<UserRespVO>>
    fun handle(query: UserByIdQuery): Uni<SystemUserEntity?>
    fun handle(query: UserByaAccountNameQuery): Uni<SystemUserEntity?>
    fun handle(query: UserByPhoneNumberQuery): Uni<SystemUserEntity?>
    fun handle(query: UserByEmailQuery): Uni<SystemUserEntity?>
    fun handle(query: UserByIdBuiltInQuery): Uni<Boolean>
    fun handle(query: UserByAccountQuery): Uni<SystemUserEntity>
}