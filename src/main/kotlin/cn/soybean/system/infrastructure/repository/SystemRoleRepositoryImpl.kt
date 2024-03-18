package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.repository.SystemRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleRepositoryImpl : SystemRoleRepository, PanacheRepository<SystemRoleEntity> {
    override fun getRoleCodesByUserId(userId: Long): Uni<Set<String>> = list(
        "select r from SystemRoleEntity r, SystemUserRoleEntity ur where r.id = ur.roleId and ur.userId = ?1",
        userId
    ).map { roles -> roles.mapNotNull { it.code }.toSet() }
}