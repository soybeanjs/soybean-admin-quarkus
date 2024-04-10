package cn.soybean.shared.projection

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import io.smallrye.mutiny.Uni

interface Projection {
    fun process(eventEntity: AggregateEventEntity): Uni<Unit>
    fun supports(eventType: String): Boolean
}