package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantRepositoryImpl : SystemTenantRepository, PanacheRepository<SystemTenantEntity> {
    override fun findByName(name: String): Uni<SystemTenantEntity> = find("name", name).singleResult()
} 