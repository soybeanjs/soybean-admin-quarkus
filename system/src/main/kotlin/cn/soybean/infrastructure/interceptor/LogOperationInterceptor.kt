/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.infrastructure.interceptor

import cn.soybean.infrastructure.interceptor.dto.OperationLogDTO
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.interfaces.rest.util.getClientIPAndPort
import cn.soybean.shared.infrastructure.annotations.LogOperation
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ResourceInfo
import jakarta.ws.rs.ext.Provider
import java.time.LocalDateTime
import org.jboss.resteasy.reactive.server.ServerRequestFilter
import org.jboss.resteasy.reactive.server.ServerResponseFilter

@Provider
@ApplicationScoped
@Priority(1)
class ReactiveLoggingInterceptor(
    private val routingContext: RoutingContext,
    private val resourceInfo: ResourceInfo,
    private val loginHelper: LoginHelper,
    private val eventBus: Event<OperationLogDTO>,
) {
    companion object {
        private const val START_TIME_PROPERTY = "startTime"
        private const val START_DATE_TIME_PROPERTY = "startDateTime"
    }

    @ServerRequestFilter
    fun filterRequest(requestContext: ContainerRequestContext) {
        val loggable = getLoggableMetadata()
        if (loggable != null) {
            requestContext.setProperty(START_TIME_PROPERTY, System.nanoTime())
            requestContext.setProperty(START_DATE_TIME_PROPERTY, LocalDateTime.now())
        }
    }

    @ServerResponseFilter
    fun filterResponse(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        val loggable = getLoggableMetadata()
        if (loggable != null) {
            val startTime = requestContext.getProperty(START_TIME_PROPERTY) as Long
            val startDateTime = requestContext.getProperty(START_DATE_TIME_PROPERTY) as LocalDateTime
            val processingTime = System.nanoTime() - startTime
            val processingTimeMillis = processingTime / 1_000_000.0
            val formattedTime = String.format("%.2f ms", processingTimeMillis)

            val requestInfo = extractRequestInfo(loggable.logReqParams, requestContext)
            val responseInfo = extractResponseInfo(loggable.logResBody, responseContext)

            var userId: String? = null
            var accountName = "api"
            var tenantId: String? = null
            try {
                userId = loginHelper.getUserId()
                accountName = loginHelper.getAccountName()
                tenantId = loginHelper.getTenantId()
            } catch (_: Exception) {
            }

            eventBus.fireAsync(
                OperationLogDTO(
                    userId = userId,
                    accountName = accountName,
                    tenantId = tenantId,
                    moduleName = loggable.moduleName,
                    description = loggable.description,
                    method = requestContext.method,
                    path = requestContext.uriInfo.path,
                    userIp = getClientIPAndPort(routingContext.request()).first,
                    userAgent = requestContext.getHeaderString("User-Agent") ?: "Unknown",
                    resourceClass = resourceInfo.resourceClass.name,
                    resourceMethod = resourceInfo.resourceMethod.name,
                    startTime = startDateTime,
                    requestInfo = requestInfo,
                    responseInfo = responseInfo,
                    processingTime = formattedTime,
                    resultCode = responseContext.status,
                    resultMsg = responseContext.statusInfo.reasonPhrase,
                ),
            )
        }
    }

    private fun extractRequestInfo(logReqParams: Boolean, context: ContainerRequestContext): String? = when {
        logReqParams ->
            context.uriInfo.queryParameters.entries.joinToString("&") { entry ->
                entry.value.joinToString("&") { value -> "${entry.key}=$value" }
            }

        else -> null
    }

    private fun extractResponseInfo(logResBody: Boolean, context: ContainerResponseContext): String? = when {
        logResBody -> Json.encode(context.entity)

        else -> null
    }

    private fun getLoggableMetadata(): LogOperation? {
        val resourceMethod = resourceInfo.resourceMethod
        return when {
            resourceMethod != null && resourceMethod.isAnnotationPresent(LogOperation::class.java) ->
                resourceMethod.getAnnotation(
                    LogOperation::class.java,
                )

            else -> null
        }
    }
}
