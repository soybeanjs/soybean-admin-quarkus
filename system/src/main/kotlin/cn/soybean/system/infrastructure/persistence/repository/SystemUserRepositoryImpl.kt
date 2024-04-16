package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.repository.SystemUserRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemUserRepositoryImpl : SystemUserRepository, PanacheRepositoryBase<SystemUserEntity, String> {
    override fun findByAccountNameOrEmailOrPhoneNumber(username: String, tenantId: String): Uni<SystemUserEntity> =
        find(
            "tenantId = ?1 and (accountName = ?2 or email = ?2 or phoneNumber = ?2)",
            tenantId,
            username
        ).singleResult()

    override fun getUserList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemUserEntity> =
        find(query, sort, params)

    override fun getById(id: String, tenantId: String): Uni<SystemUserEntity?> =
        find("id = ?1 and tenantId = ?2", id, tenantId).firstResult()

    override fun getByAccountName(accountName: String, tenantId: String): Uni<SystemUserEntity?> =
        find("accountName = ?1 and tenantId = ?2", accountName, tenantId).firstResult()

    override fun getByPhoneNumber(phoneNumber: String, tenantId: String): Uni<SystemUserEntity?> =
        find("phoneNumber = ?1 and tenantId = ?2", phoneNumber, tenantId).firstResult()

    override fun getByEmail(email: String, tenantId: String): Uni<SystemUserEntity?> =
        find("email = ?1 and tenantId = ?2", email, tenantId).firstResult()

    override fun saveOrUpdate(entity: SystemUserEntity): Uni<SystemUserEntity> = persist(entity)

    override fun delById(id: String, tenantId: String): Uni<Long> = delete("id = ?1 and tenantId = ?2", id, tenantId)
}