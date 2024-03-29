package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemLoginLogEntity
import cn.soybean.system.domain.repository.SystemLoginLogRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemLoginLogRepositoryImpl : SystemLoginLogRepository, PanacheRepository<SystemLoginLogEntity> 