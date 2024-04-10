package cn.soybean.domain.event

import cn.soybean.shared.domain.event.DomainEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event

@ApplicationScoped
class DomainEventPublisher(private val event: Event<DomainEvent>) {
    fun publish(event: DomainEvent) {
        this.event.fireAsync(event)
    }
}