package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleMenuRepositoryImpl : SystemRoleMenuRepository, PanacheRepositoryBase<SystemRoleMenuEntity, String> {
    override fun delByRoleId(roleId: String, tenantId: String): Uni<Long> =
        delete("roleId = ?1 and tenantId = ?2", roleId, tenantId)
}