package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemDeptEntity
import cn.soybean.domain.system.repository.SystemDeptRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDeptRepositoryImpl : SystemDeptRepository, PanacheRepositoryBase<SystemDeptEntity, String>
