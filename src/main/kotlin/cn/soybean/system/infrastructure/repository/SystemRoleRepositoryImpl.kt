package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.repository.SystemRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleRepositoryImpl : SystemRoleRepository, PanacheRepository<SystemRoleEntity>