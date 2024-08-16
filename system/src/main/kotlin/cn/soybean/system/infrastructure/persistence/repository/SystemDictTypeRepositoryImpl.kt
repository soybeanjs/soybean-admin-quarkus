package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemDictTypeEntity
import cn.soybean.domain.system.repository.SystemDictTypeRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDictTypeRepositoryImpl : SystemDictTypeRepository, PanacheRepositoryBase<SystemDictTypeEntity, String>
