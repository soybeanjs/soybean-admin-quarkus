package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantRepositoryImpl : SystemTenantRepository, PanacheRepository<SystemTenantEntity> 