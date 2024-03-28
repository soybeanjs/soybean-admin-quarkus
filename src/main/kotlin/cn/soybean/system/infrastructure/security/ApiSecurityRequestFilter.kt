package cn.soybean.system.infrastructure.security

import cn.soybean.framework.common.consts.AppConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.response.ResponseEntity
import cn.soybean.system.infrastructure.localization.LocalizationService
import cn.soybean.system.infrastructure.util.SignUtil
import cn.soybean.system.interfaces.annotations.ApiKeyRequest
import cn.soybean.system.interfaces.annotations.ApiSignRequest
import io.quarkus.redis.client.RedisClientName
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.quarkus.redis.datasource.value.SetArgs
import io.smallrye.mutiny.Uni
import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ResourceInfo
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerRequestFilter
import java.time.Duration
import java.time.Instant
import kotlin.math.abs

data class ApiRequestAnnotation(val type: ApiRequestType, val keyName: String = "")

enum class ApiRequestType {
    API_KEY, API_SIGN, NONE
}

/**
 * API安全请求过滤器
 * 用于验证API请求的安全性
 * 1. 验证API密钥
 * 2. 验证API签名
 * 此处每个请求都会被验证, 如果追求性能, 可以参考quarkus官网案例使用@NameBinding注解, 仅对特定的请求进行验证, 此处直接基于JAX-RS实现
 * @author bytebytebrew
 * @date 2024/03/20
 * @constructor 创建[ApiSecurityRequestFilter]
 * @param [resourceInfo] 资源信息
 * @param [apiKeyCache] API密钥缓存
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
class ApiSecurityRequestFilter(
    private val resourceInfo: ResourceInfo,
    private val apiKeyCache: ApiKeyCache,
    @RedisClientName("sign-redis") private val reactiveRedisDataSource: ReactiveRedisDataSource,
    private val i18n: LocalizationService
) {

    @ServerRequestFilter
    fun apiKeyRequest(requestContext: ContainerRequestContext): Uni<Response> {
        val apiRequestAnnotation = getApiRequestAnnotation()
        return when (apiRequestAnnotation.type) {
            ApiRequestType.API_KEY -> validateHeaderAndParams(requestContext, apiRequestAnnotation.keyName)

            ApiRequestType.API_SIGN -> validateSignRequest(requestContext)

            else -> Uni.createFrom().nullItem()
        }
    }

    private fun getApiRequestAnnotation(): ApiRequestAnnotation {
        val resourceMethod = resourceInfo.resourceMethod ?: return ApiRequestAnnotation(ApiRequestType.NONE)
        return when {
            resourceMethod.isAnnotationPresent(ApiKeyRequest::class.java) ->
                ApiRequestAnnotation(
                    ApiRequestType.API_KEY,
                    resourceMethod.getAnnotation(ApiKeyRequest::class.java).keyName
                )

            resourceMethod.isAnnotationPresent(ApiSignRequest::class.java) -> ApiRequestAnnotation(ApiRequestType.API_SIGN)
            else -> ApiRequestAnnotation(ApiRequestType.NONE)
        }
    }

    private fun validateHeaderAndParams(context: ContainerRequestContext, keyName: String): Uni<Response> {
        val keyValue = context.getHeaderString(keyName) ?: context.uriInfo.queryParameters.getFirst(keyName)
        return keyValue?.let {
            val (isValid, errorMessage) = validateApiKeyValue(it, context)
            when {
                isValid -> Uni.createFrom().nullItem()
                else -> Uni.createFrom().item(badRequestResponse(errorMessage))
            }
        } ?: Uni.createFrom().item(badRequestResponse(i18n.getMessage("http.security.apiKeyMissing", keyName)))
    }

    private fun validateApiKeyValue(
        keyValue: String,
        context: ContainerRequestContext
    ): Triple<Boolean, String, String> {
        val apikeyEntity = apiKeyCache.get(keyValue)
        return when {
            apikeyEntity == null -> Triple(false, i18n.getMessage("http.security.apiKeyNotExists"), "")

            apikeyEntity.status != DbEnums.Status.ENABLED -> Triple(
                false,
                i18n.getMessage("http.security.apiKeyDisabled"),
                ""
            )

            else -> {
                context.headers.putSingle(
                    AppConstants.API_TENANT_ID,
                    apikeyEntity.tenantId?.toString() ?: "defaultTenantId"
                )
                Triple(true, "", apikeyEntity.apiSecret)
            }
        }
    }

    private fun validateSignRequest(context: ContainerRequestContext): Uni<Response> {
        val (isValid, errorMessage) = validateHeaderAndParams(context)
        return when {
            !isValid -> Uni.createFrom().item(badRequestResponse(errorMessage))
            else -> validateTimestamp(context.headers.getFirst(AppConstants.API_TIMESTAMP))
                .flatMap { (isValidTimestamp, timestampErrorMessage) ->
                    when {
                        isValidTimestamp -> validateNonce(context.headers.getFirst(AppConstants.API_NONCE))
                            .flatMap { (isValidNonce, nonceErrorMessage) ->
                                when {
                                    isValidNonce -> validateSignature(context)
                                    else -> Uni.createFrom().item(badRequestResponse(nonceErrorMessage))
                                }
                            }

                        else -> Uni.createFrom().item(badRequestResponse(timestampErrorMessage))
                    }
                }
        }
    }

    private fun validateHeaderAndParams(context: ContainerRequestContext): Pair<Boolean, String> {
        val headerChecks = listOf(
            AppConstants.API_KEY to i18n.getMessage("http.security.apiKeyNotIncluded"),
            AppConstants.API_SIGNATURE to i18n.getMessage("http.security.signatureMissing"),
            AppConstants.API_TIMESTAMP to i18n.getMessage("http.security.timestampMissing"),
            AppConstants.API_NONCE to i18n.getMessage("http.security.nonceMissing")
        )

        headerChecks.forEach { (headerName, errorMessage) ->
            context.getHeaderString(headerName) ?: return Pair(false, errorMessage)
        }

        return Pair(true, "")
    }

    private fun validateTimestamp(timestamp: String): Uni<Pair<Boolean, String>> = when {
        abs(
            Instant.now().toEpochMilli() - timestamp.toLong()
        ) > AppConstants.API_TIMESTAMP_DISPARITY -> Uni.createFrom()
            .item(Pair(false, i18n.getMessage("http.security.timestampOff")))

        else -> Uni.createFrom().item(Pair(true, ""))
    }

    private fun validateNonce(nonce: String): Uni<Pair<Boolean, String>> =
        reactiveRedisDataSource.key().exists("${AppConstants.API_NONCE_CACHE_PREFIX}::$nonce").flatMap { exists ->
            when {
                exists -> Uni.createFrom().item(Pair(false, i18n.getMessage("http.security.nonceUsed")))

                else -> Uni.createFrom().item(Pair(true, ""))
            }
        }

    @Suppress("kotlin:S6518")
    private fun validateSignature(context: ContainerRequestContext): Uni<Response> {
        val apiKey = context.getHeaderString(AppConstants.API_KEY)
        val apiSignature = context.getHeaderString(AppConstants.API_SIGNATURE)
        val apiNonce = context.getHeaderString(AppConstants.API_NONCE)
        val algorithm = context.getHeaderString(AppConstants.API_ALGORITHM) ?: "HmacSHA256"
        // 所有query参数 并且请求头固定参数也要参与加签
        val allQueryParams = mutableMapOf<String, String>()
        context.uriInfo.queryParameters.forEach { (key, values) ->
            values.firstOrNull()?.let { value -> allQueryParams[key] = value }
        }
        allQueryParams[AppConstants.API_KEY] = apiKey
        allQueryParams[AppConstants.API_TIMESTAMP] = context.getHeaderString(AppConstants.API_TIMESTAMP)
        allQueryParams[AppConstants.API_NONCE] = apiNonce
        val (isValid, errorMessage, apiSecret) = validateApiKeyValue(apiKey, context)
        return when {
            isValid -> {
                val calculatedSign = SignUtil.createSign(allQueryParams, algorithm, apiSecret)
                when (calculatedSign) {
                    apiSignature -> reactiveRedisDataSource.value(String::class.java)
                        .set(
                            "${AppConstants.API_NONCE_CACHE_PREFIX}::$apiNonce",
                            "1",
                            SetArgs().nx()
                                .ex(Duration.ofMillis(AppConstants.API_TIMESTAMP_DISPARITY + AppConstants.API_TIMESTAMP_EXTRA_TIME_MARGIN))
                        )
                        .flatMap { Uni.createFrom().nullItem() }

                    else -> Uni.createFrom()
                        .item(badRequestResponse(i18n.getMessage("http.security.signatureOff")))
                }
            }

            else -> Uni.createFrom().item(badRequestResponse(errorMessage))
        }
    }

    private fun badRequestResponse(message: String): Response = Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(ResponseEntity.fail<Any>(message))
        .build()
}