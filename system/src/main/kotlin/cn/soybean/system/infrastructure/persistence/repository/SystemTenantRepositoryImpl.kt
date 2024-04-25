package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantRepositoryImpl : SystemTenantRepository, PanacheRepositoryBase<SystemTenantEntity, String> {
    override fun findByName(name: String): Uni<SystemTenantEntity> = find("name", name).singleResult()
    override fun getTenantList(query: String, params: Parameters): PanacheQuery<SystemTenantEntity> =
        find(query, params)
} 