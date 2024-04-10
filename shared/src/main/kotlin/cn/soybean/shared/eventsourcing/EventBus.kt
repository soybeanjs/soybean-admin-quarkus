package cn.soybean.shared.eventsourcing

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import io.smallrye.mutiny.Uni

fun interface EventBus {
    fun publish(eventEntities: MutableList<AggregateEventEntity>): Uni<Unit>
}