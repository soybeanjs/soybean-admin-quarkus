package cn.soybean

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.shared.infrastructure.annotations.ApiKeyRequest
import cn.soybean.shared.infrastructure.annotations.ApiSignRequest
import io.quarkus.logging.Log
import io.quarkus.security.PermissionsAllowed
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/hello")
@Tag(name = "Demo", description = "Operations related to demos")
class ExampleResource {

    @PermissionsAllowed("${AppConstants.APP_PERM_NAME_PREFIX}hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
        summary = "@PermissionsAllowed注解权限验证",
        description = "默认使用[io.quarkus.security.StringPermission],也即默认按照冒号分割资源和权限,例user:read(user代表资源,read代表动作"
    )
    fun hello(): Uni<String> = uni { "Hello from RESTEasy Reactive" }

    @ApiSignRequest
    @Path("/sign")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "sign验证", description = "加签请求验证")
    fun sign() = "sign request success"

    /**
     * 多租户系统偏重业务，此处示例在使用apiKey或者sign方案对接接口是通过拦截器中继到context上下文，业务手动处理
     * @param [context] 上下文
     * @return [String]
     */
    @ApiKeyRequest
    @Path("/apiKey")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "apiKey验证", description = "apiKey请求验证")
    fun apiKey(@Context context: ContainerRequestContext): String {
        val tenantId = context.getHeaderString(AppConstants.API_TENANT_ID)
        Log.info("Tenant ID from header: ${tenantId ?: "Not provided"}")

        return "apiKey request success"
    }
}