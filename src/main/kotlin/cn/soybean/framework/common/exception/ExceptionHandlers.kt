package cn.soybean.framework.common.exception

import cn.soybean.framework.common.base.ResponseEntity
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.persistence.NoResultException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

@Provider
class ExceptionHandlers {

    @ServerExceptionMapper
    fun handleNoResultException(e: NoResultException): Uni<Response> = uni {
        Response.ok()
            .entity(
                ResponseEntity.fail<Any>(
                    Response.Status.NO_CONTENT.statusCode.toString(),
                    Response.Status.NO_CONTENT.reasonPhrase
                )
            )
            .build()
    }

    @ServerExceptionMapper
    fun handleServiceException(e: ServiceException): Uni<Response> = uni {
        Response.ok()
            .entity(ResponseEntity.fail<Any>(e.message))
            .build()
    }
}