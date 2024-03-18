package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemTenantPackageEntity
import cn.soybean.system.domain.repository.SystemTenantPackageRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantPackageRepositoryImpl : SystemTenantPackageRepository, PanacheRepository<SystemTenantPackageEntity> 