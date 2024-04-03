package cn.soybean.domain

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import jakarta.enterprise.inject.Instance

interface DomainEvent

interface DomainEventListener<E : DomainEvent> {
    fun onEvent(event: E)
    fun supports(event: DomainEvent): Boolean
}

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