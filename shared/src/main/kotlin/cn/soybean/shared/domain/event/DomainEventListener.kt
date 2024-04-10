package cn.soybean.shared.domain.event

interface DomainEventListener<E : DomainEvent> {
    fun onEvent(event: E)
    fun supports(event: DomainEvent): Boolean
}