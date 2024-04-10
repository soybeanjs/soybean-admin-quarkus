package cn.soybean.shared.application.query

import io.smallrye.mutiny.Uni

interface QueryHandler<C : Query, R> {
    fun handle(query: C): Uni<R>
    fun canHandle(query: Query): Boolean
}