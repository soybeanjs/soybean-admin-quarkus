package cn.soybean.system.application.query.tenant.handler

import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantQueryHandler(private val systemTenantRepository: SystemTenantRepository) : TenantQueryService {
    override fun handle(query: TenantByNameQuery): Uni<SystemTenantEntity> =
        systemTenantRepository.findByName(query.name)
}