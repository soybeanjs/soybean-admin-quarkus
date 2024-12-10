/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.domain.event

interface DomainEventListener<E : DomainEvent> {
    fun onEvent(event: E)

    fun supports(event: DomainEvent): Boolean
}
