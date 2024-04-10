package cn.soybean.domain

import cn.soybean.shared.domain.event.DomainEvent
import cn.soybean.shared.domain.event.DomainEventListener
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import jakarta.enterprise.inject.Instance

@ApplicationScoped
class EventInvoker(private val handlers: Instance<DomainEventListener<*>>) {

    fun distributionProcess(event: DomainEvent) {
        handlers.forEach { handler ->
            if (handler.supports(event)) {
                callHandlerSafely(handler, event)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : DomainEvent> callHandlerSafely(handler: DomainEventListener<*>, event: E) {
        (handler as DomainEventListener<E>).onEvent(event)
    }
}

@ApplicationScoped
class DomainEventPublisher(private val event: Event<DomainEvent>) {
    fun publish(event: DomainEvent) {
        this.event.fireAsync(event)
    }
}