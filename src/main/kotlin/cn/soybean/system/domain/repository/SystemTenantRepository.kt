package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemTenantEntity
import io.smallrye.mutiny.Uni

interface SystemTenantRepository {
    fun findByName(name: String): Uni<SystemTenantEntity>
}