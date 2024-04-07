package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemTenantPackageEntity
import cn.soybean.system.domain.repository.SystemTenantPackageRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemTenantPackageRepositoryImpl : SystemTenantPackageRepository,
    PanacheRepositoryBase<SystemTenantPackageEntity, String> 