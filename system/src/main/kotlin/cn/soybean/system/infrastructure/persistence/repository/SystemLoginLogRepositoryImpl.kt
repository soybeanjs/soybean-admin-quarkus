package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemLoginLogEntity
import cn.soybean.domain.system.repository.SystemLoginLogRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemLoginLogRepositoryImpl : SystemLoginLogRepository, PanacheRepositoryBase<SystemLoginLogEntity, String>
