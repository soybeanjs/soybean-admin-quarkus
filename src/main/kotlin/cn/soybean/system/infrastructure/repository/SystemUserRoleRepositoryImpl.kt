package cn.soybean.system.infrastructure.repository

import cn.soybean.system.domain.entity.SystemUserRoleEntity
import cn.soybean.system.domain.repository.SystemUserRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemUserRoleRepositoryImpl : SystemUserRoleRepository, PanacheRepository<SystemUserRoleEntity>