package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemRoleMenuEntity
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemRoleMenuRepositoryImpl : SystemRoleMenuRepository, PanacheRepositoryBase<SystemRoleMenuEntity, String>