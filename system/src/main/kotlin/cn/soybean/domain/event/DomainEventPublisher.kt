/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
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
