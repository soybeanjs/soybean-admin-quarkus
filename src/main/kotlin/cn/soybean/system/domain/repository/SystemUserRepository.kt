package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemUserEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni

interface SystemUserRepository {
    fun findByAccountNameOrEmailOrPhoneNumber(username: String, tenantId: Long): Uni<SystemUserEntity>
    fun getUserList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemUserEntity>
}