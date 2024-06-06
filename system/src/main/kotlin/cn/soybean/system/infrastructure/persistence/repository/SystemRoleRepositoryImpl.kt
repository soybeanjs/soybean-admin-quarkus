package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.repository.SystemRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleRepositoryImpl : SystemRoleRepository, PanacheRepositoryBase<SystemRoleEntity, String> {
    override fun getRoleCodesByUserId(userId: String): Uni<Set<String>> = list(
        "select r from SystemRoleEntity r, SystemRoleUserEntity ur where r.id = ur.roleId and ur.userId = ?1",
        userId
    ).map { roles -> roles.mapNotNull { it.code }.toSet() }

    override fun getRoleList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemRoleEntity> =
        find(query, sort, params)

    override fun saveOrUpdate(entity: SystemRoleEntity): Uni<SystemRoleEntity> = persist(entity)
    override fun getById(id: String, tenantId: String): Uni<SystemRoleEntity?> =
        find("id = ?1 and tenantId = ?2", id, tenantId).firstResult()

    override fun existsByCode(code: String, tenantId: String): Uni<Boolean> =
        find("code = ?1 and tenantId = ?2", code, tenantId).count().map {
            when {
                it > 0 -> true
                else -> false
            }
        }

    override fun delById(id: String, tenantId: String): Uni<Long> = delete("id = ?1 and tenantId = ?2", id, tenantId)

    override fun getByCode(tenantId: String, code: String): Uni<SystemRoleEntity> =
        find("tenantId = ?1 and code = ?2", tenantId, code).singleResult()
}
