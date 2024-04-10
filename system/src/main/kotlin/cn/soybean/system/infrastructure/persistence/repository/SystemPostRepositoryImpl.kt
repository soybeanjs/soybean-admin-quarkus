package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemPostEntity
import cn.soybean.system.domain.repository.SystemPostRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemPostRepositoryImpl : SystemPostRepository, PanacheRepositoryBase<SystemPostEntity, String> 