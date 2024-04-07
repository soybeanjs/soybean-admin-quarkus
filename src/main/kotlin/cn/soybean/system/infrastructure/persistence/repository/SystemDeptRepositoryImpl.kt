package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.system.domain.entity.SystemDeptEntity
import cn.soybean.system.domain.repository.SystemDeptRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDeptRepositoryImpl : SystemDeptRepository, PanacheRepositoryBase<SystemDeptEntity, String> 