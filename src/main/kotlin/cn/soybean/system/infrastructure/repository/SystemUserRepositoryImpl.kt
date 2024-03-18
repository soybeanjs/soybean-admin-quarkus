package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.repository.SystemUserRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemUserRepositoryImpl : SystemUserRepository, PanacheRepository<SystemUserEntity>