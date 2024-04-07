package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemUserRoleEntity
import cn.soybean.system.domain.repository.SystemUserRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemUserRoleRepositoryImpl : SystemUserRoleRepository, PanacheRepositoryBase<SystemUserRoleEntity, String>