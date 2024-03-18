package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemDictDataEntity
import cn.soybean.system.domain.repository.SystemDictDataRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDictDataRepositoryImpl : SystemDictDataRepository, PanacheRepository<SystemDictDataEntity> 