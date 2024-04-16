package cn.soybean.system.application.query.user.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.user.PageUserQuery
import cn.soybean.system.application.query.user.UserByEmail
import cn.soybean.system.application.query.user.UserById
import cn.soybean.system.application.query.user.UserByIdBuiltInQuery
import cn.soybean.system.application.query.user.UserByPhoneNumber
import cn.soybean.system.application.query.user.UserByaAccountName
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.interfaces.rest.vo.UserRespVO
import io.smallrye.mutiny.Uni

interface UserQueryService {
    fun handle(query: PageUserQuery): Uni<PageResult<UserRespVO>>
    fun handle(query: UserById): Uni<SystemUserEntity?>
    fun handle(query: UserByaAccountName): Uni<SystemUserEntity?>
    fun handle(query: UserByPhoneNumber): Uni<SystemUserEntity?>
    fun handle(query: UserByEmail): Uni<SystemUserEntity?>
    fun handle(query: UserByIdBuiltInQuery): Uni<Boolean>
}