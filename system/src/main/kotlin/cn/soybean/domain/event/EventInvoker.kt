package cn.soybean.domain.event

import cn.soybean.shared.domain.event.DomainEvent
import cn.soybean.shared.domain.event.DomainEventListener
import jakarta.enterprise.context.ApplicationScoped
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

