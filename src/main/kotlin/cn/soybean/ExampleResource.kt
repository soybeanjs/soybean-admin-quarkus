package cn.soybean

import cn.soybean.system.interfaces.annotations.ApiKeyRequest
import cn.soybean.system.interfaces.annotations.ApiSignRequest
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation

@Path("/hello")
class ExampleResource {

    @ApiSignRequest
    @Path("/sign")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "sign验证", description = "加签请求验证")
    fun sign() = "sign request success"

    @ApiKeyRequest
    @Path("/apiKey")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "apiKey验证", description = "apiKey请求验证")
    fun apiKey() = "apiKey request success"
}