/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest.exceptions

import cn.soybean.application.exceptions.ServiceException
import cn.soybean.interfaces.rest.response.ResponseEntity
import io.quarkus.logging.Log
import io.quarkus.security.ForbiddenException
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.persistence.NoResultException
import jakarta.ws.rs.NotAllowedException
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

@Provider
class ExceptionHandlers {
    @ServerExceptionMapper
    fun handleException(e: Exception): Uni<Response> =
        uni {
            Log.error(e.printStackTrace())
            Response
                .ok()
                .entity(ResponseEntity.fail<Any>("Exception"))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build()
        }

    @ServerExceptionMapper
    fun handleNoResultException(e: NoResultException): Uni<Response> =
        uni {
            Response
                .ok()
                .entity(ResponseEntity.fail<Any>(Status.NO_CONTENT.reasonPhrase))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build()
        }

    @ServerExceptionMapper
    fun handleServiceException(e: ServiceException): Uni<Response> =
        uni {
            Response
                .ok()
                .entity(ResponseEntity.fail<Any>(e.message ?: "Unknown"))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build()
        }

    @ServerExceptionMapper
    fun handleForbiddenException(e: ForbiddenException): Uni<Response> =
        uni {
            val defaultMessage =
                """
                Uh-oh! You've hit a roadblock. This area is a bit exclusive,
                but we're here to help you find where you need to go.
                Reach out if you think you should have access.
                """.trimIndent()

            Response
                .status(Status.FORBIDDEN)
                .entity(
                    ResponseEntity.error<Any>(
                        Status.FORBIDDEN.statusCode.toString(),
                        e.message ?: defaultMessage,
                    ),
                ).header("Content-Type", MediaType.APPLICATION_JSON)
                .build()
        }

    @ServerExceptionMapper
    fun handleNotAllowedException(e: NotAllowedException): Uni<Response> =
        uni {
            Response
                .status(Status.METHOD_NOT_ALLOWED)
                .entity(
                    ResponseEntity.error<Any>(
                        Status.METHOD_NOT_ALLOWED.statusCode.toString(),
                        Status.METHOD_NOT_ALLOWED.reasonPhrase,
                    ),
                ).header("Content-Type", MediaType.APPLICATION_JSON)
                .build()
        }
}
