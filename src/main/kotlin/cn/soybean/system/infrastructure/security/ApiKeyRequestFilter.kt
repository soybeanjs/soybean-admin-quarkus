package cn.soybean.system.infrastructure.security

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.interfaces.annotations.ApiKeyRequest
import io.smallrye.mutiny.Uni
import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ResourceInfo
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerRequestFilter

@Provider
@Priority(Priorities.AUTHENTICATION)
class ApiKeyRequestFilter(private val resourceInfo: ResourceInfo, private val apiKeyCache: ApiKeyCache) {

    @ServerRequestFilter
    fun apiKeyRequest(requestContext: ContainerRequestContext): Uni<Response> = getSignRequestMetadata()?.let { sign ->
        validateHeaderAndParams(requestContext, sign.keyName)
    } ?: Uni.createFrom().nullItem()

    private fun getSignRequestMetadata(): ApiKeyRequest? {
        val resourceMethod = resourceInfo.resourceMethod
        return when {
            resourceMethod != null && resourceMethod.isAnnotationPresent(ApiKeyRequest::class.java) -> resourceMethod.getAnnotation(
                ApiKeyRequest::class.java
            )

            else -> null
        }
    }

    private fun validateHeaderAndParams(context: ContainerRequestContext, keyName: String): Uni<Response> {
        val keyValue = context.getHeaderString(keyName) ?: context.uriInfo.queryParameters.getFirst(keyName)
        return keyValue?.let {
            val (isValid, errorMessage) = validateApiKeyValue(it)
            when {
                isValid -> Uni.createFrom().nullItem()
                else -> Uni.createFrom().item(
                    badRequestResponse(
                        errorMessage ?: "API key validation failed unexpectedly. Please try again."
                    )
                )
            }
        } ?: Uni.createFrom()
            .item(badRequestResponse("Oops! Looks like we're missing the '$keyName' API key needed to access this feature. Could you double-check and include it either in your request headers or as a query parameter? Thanks!"))
    }

    private fun validateApiKeyValue(keyValue: String): Pair<Boolean, String?> {
        val apikey = apiKeyCache.get(keyValue)
        return when {
            apikey == null -> Pair(
                false,
                "The API key provided does not match any active records. Please check and try again."
            )

            apikey.status != DbEnums.Status.ENABLED -> Pair(
                false,
                "This API key has been disabled. Contact support if you believe this is an error."
            )

            else -> Pair(true, null)
        }
    }

    private fun badRequestResponse(message: String): Response = Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(ResponseEntity.fail<Any>(message))
        .build()
}