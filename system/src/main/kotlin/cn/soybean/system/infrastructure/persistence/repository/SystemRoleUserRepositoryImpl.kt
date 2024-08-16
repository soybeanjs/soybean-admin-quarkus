package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemRoleUserEntity
import cn.soybean.domain.system.repository.SystemRoleUserRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleUserRepositoryImpl : SystemRoleUserRepository, PanacheRepositoryBase<SystemRoleUserEntity, String> {
    override fun delByRoleId(roleId: String, tenantId: String): Uni<Long> =
        delete("roleId = ?1 and tenantId = ?2", roleId, tenantId)

    override fun saveOrUpdate(roleUser: SystemRoleUserEntity): Uni<SystemRoleUserEntity> = persist(roleUser)
}
