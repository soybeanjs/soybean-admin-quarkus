package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.repository.SystemMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemMenuRepositoryImpl : SystemMenuRepository, PanacheRepository<SystemMenuEntity> 