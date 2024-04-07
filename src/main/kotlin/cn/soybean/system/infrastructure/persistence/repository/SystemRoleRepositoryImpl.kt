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
        "select r from SystemRoleEntity r, SystemUserRoleEntity ur where r.id = ur.roleId and ur.userId = ?1",
        userId
    ).map { roles -> roles.mapNotNull { it.code }.toSet() }

    override fun getRoleList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemRoleEntity> =
        find(query, sort, params)

    override fun saveOrUpdate(entity: SystemRoleEntity): Uni<SystemRoleEntity> = persist(entity)
    override fun getById(id: String): Uni<SystemRoleEntity> = findById(id)
}