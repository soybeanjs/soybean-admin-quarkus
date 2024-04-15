package cn.soybean.system.application.query.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageUserQuery
import cn.soybean.system.application.query.UserByEmail
import cn.soybean.system.application.query.UserById
import cn.soybean.system.application.query.UserByPhoneNumber
import cn.soybean.system.application.query.UserByaAccountName
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.interfaces.rest.vo.UserRespVO
import io.smallrye.mutiny.Uni

interface UserQueryService {
    fun handle(query: PageUserQuery): Uni<PageResult<UserRespVO>>
    fun handle(query: UserById): Uni<SystemUserEntity?>
    fun handle(query: UserByaAccountName): Uni<SystemUserEntity?>
    fun handle(query: UserByPhoneNumber): Uni<SystemUserEntity?>
    fun handle(query: UserByEmail): Uni<SystemUserEntity?>
}