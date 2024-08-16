package cn.soybean.system.application.query.tenant.service

import cn.soybean.domain.system.entity.SystemTenantEntity
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.tenant.PageTenantQuery
import cn.soybean.system.application.query.tenant.TenantByIdBuiltInQuery
import cn.soybean.system.application.query.tenant.TenantByIdQuery
import cn.soybean.system.application.query.tenant.TenantByNameExistsQuery
import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.smallrye.mutiny.Uni

interface TenantQueryService {
    fun handle(query: TenantByNameQuery): Uni<SystemTenantEntity>
    fun handle(query: PageTenantQuery): Uni<PageResult<TenantResponse>>
    fun handle(query: TenantByNameExistsQuery): Uni<Boolean>
    fun handle(query: TenantByIdBuiltInQuery): Uni<Boolean>
    fun handle(query: TenantByIdQuery): Uni<SystemTenantEntity>
}
