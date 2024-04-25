package cn.soybean.system.application.query.tenant.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.tenant.PageTenantQuery
import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.smallrye.mutiny.Uni

interface TenantQueryService {
    fun handle(query: TenantByNameQuery): Uni<SystemTenantEntity>
    fun handle(query: PageTenantQuery): Uni<PageResult<TenantResponse>>
}