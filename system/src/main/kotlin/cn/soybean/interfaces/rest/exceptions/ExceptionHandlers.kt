package cn.soybean.interfaces.rest.exceptions

import cn.soybean.application.exceptions.ServiceException
import cn.soybean.interfaces.rest.response.ResponseEntity
import io.quarkus.security.ForbiddenException
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.persistence.NoResultException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

@Provider
class ExceptionHandlers {

    @ServerExceptionMapper
    fun handleNoResultException(e: NoResultException): Uni<Response> = uni {
        Response.ok()
            .entity(ResponseEntity.fail<Any>(Status.NO_CONTENT.reasonPhrase))
            .build()
    }

    @ServerExceptionMapper
    fun handleServiceException(e: ServiceException): Uni<Response> = uni {
        Response.ok()
            .entity(ResponseEntity.fail<Any>(e.message ?: "Unknown"))
            .build()
    }

    @ServerExceptionMapper
    fun handleForbiddenException(e: ForbiddenException): Uni<Response> = uni {
        Response.ok()
            .entity(
                ResponseEntity.error<Any>(
                    Status.FORBIDDEN.statusCode.toString(),
                    e.message
                        ?: "Uh-oh! You've hit a roadblock. This area is a bit exclusive, but we're here to help you find where you need to go. Reach out if you think you should have access."
                )
            ).build()
    }
}
