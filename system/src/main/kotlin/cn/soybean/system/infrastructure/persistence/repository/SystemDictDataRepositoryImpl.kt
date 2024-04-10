package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemDictDataEntity
import cn.soybean.system.domain.repository.SystemDictDataRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDictDataRepositoryImpl : SystemDictDataRepository, PanacheRepositoryBase<SystemDictDataEntity, String> 