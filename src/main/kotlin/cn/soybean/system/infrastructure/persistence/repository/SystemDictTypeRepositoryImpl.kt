package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemDictTypeEntity
import cn.soybean.system.domain.repository.SystemDictTypeRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDictTypeRepositoryImpl : SystemDictTypeRepository, PanacheRepository<SystemDictTypeEntity> 