package cn.soybean.system.application.query.tenant.service

import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.domain.entity.SystemTenantEntity
import io.smallrye.mutiny.Uni

interface TenantQueryService {
    fun handle(query: TenantByNameQuery): Uni<SystemTenantEntity>
}