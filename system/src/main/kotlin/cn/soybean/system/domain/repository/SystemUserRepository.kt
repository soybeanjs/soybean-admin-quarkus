package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemUserEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni

interface SystemUserRepository {
    fun findByAccountNameOrEmailOrPhoneNumber(accountName: String, tenantId: String): Uni<SystemUserEntity>
    fun getUserList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemUserEntity>
    fun getById(id: String, tenantId: String): Uni<SystemUserEntity?>
    fun getByAccountName(accountName: String, tenantId: String): Uni<SystemUserEntity?>
    fun getByPhoneNumber(phoneNumber: String, tenantId: String): Uni<SystemUserEntity?>
    fun getByEmail(email: String, tenantId: String): Uni<SystemUserEntity?>
    fun saveOrUpdate(entity: SystemUserEntity): Uni<SystemUserEntity>
    fun delById(id: String, tenantId: String): Uni<Long>
}