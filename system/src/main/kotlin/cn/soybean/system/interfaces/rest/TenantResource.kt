package cn.soybean.system.interfaces.rest

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.interfaces.rest.response.ResponseEntity
import cn.soybean.system.application.query.tenant.PageTenantQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import cn.soybean.system.application.service.TenantService
import cn.soybean.system.interfaces.rest.dto.query.tenant.TenantQuery
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.panache.common.Parameters
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/tenant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tenants", description = "Operations related to tenants")
class TenantResource(
    private val tenantQueryService: TenantQueryService,
    private val tenantService: TenantService,
    private val loginHelper: LoginHelper
) {

    @PermissionsAllowed("${AppConstants.APP_PERM_ACTION_PREFIX}tenant.list")
    @GET
    @WithSession
    @Operation(summary = "租户列表", description = "获取租户列表")
    fun pageQuery(@Parameter @BeanParam queryParam: TenantQuery): Uni<ResponseEntity<PageResult<TenantResponse>>> {
        var query = ""
        val params = Parameters()

        queryParam.name.takeIf { !it.isNullOrEmpty() }?.let {
            query += " and name like :name"
            params.and("name", "%$it%")
        }

        queryParam.status.takeIf { it != null }?.let {
            query += " and status = status"
            params.and("status", "%$it%")
        }
        return tenantQueryService.handle(PageTenantQuery(query, params, queryParam.ofPage()))
            .map { ResponseEntity.ok(it) }
    }
}