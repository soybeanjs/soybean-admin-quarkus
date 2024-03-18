package cn.soybean.system.domain.service

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.exception.ErrorCode
import cn.soybean.framework.interfaces.exception.ServiceException
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.repository.SystemTenantRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime

@ApplicationScoped
class TenantService(private val systemTenantRepository: SystemTenantRepository) {

    fun findAndVerifyTenant(tenantName: String): Uni<SystemTenantEntity> =
        systemTenantRepository.findByName(tenantName)
            .onItem().ifNull().failWith(ServiceException(ErrorCode.TENANT_NOT_FOUND))
            .flatMap { tenant -> verifyTenantStatus(tenant) }

    fun verifyTenantStatus(tenant: SystemTenantEntity): Uni<SystemTenantEntity> = when {
        tenant.status == DbEnums.Status.DISABLED -> Uni.createFrom()
            .failure(ServiceException(ErrorCode.TENANT_DISABLED))

        LocalDateTime.now().isAfter(tenant.expireTime) ->
            Uni.createFrom().failure(ServiceException(ErrorCode.TENANT_EXPIRED))

        else -> Uni.createFrom().item(tenant)
    }
}