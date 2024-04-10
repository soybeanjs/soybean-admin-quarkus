package cn.soybean.application.query

import cn.soybean.shared.application.query.Query
import cn.soybean.shared.application.query.QueryHandler
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance

@ApplicationScoped
class QueryInvoker(private val handlers: Instance<QueryHandler<*, *>>) {

    @Suppress("UNCHECKED_CAST")
    fun <C : Query, R> dispatch(query: C): Uni<R> {
        val handler = handlers
            .stream()
            .filter { it.canHandle(query) }
            .findFirst()
            .orElseThrow { IllegalStateException("No handler found for query: ${query::class.simpleName}") }
            .let { it as QueryHandler<C, R> }

        return handler.handle(query)
    }
}