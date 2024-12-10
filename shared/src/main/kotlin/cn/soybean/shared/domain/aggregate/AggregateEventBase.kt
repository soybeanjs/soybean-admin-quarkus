/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.domain.aggregate

abstract class AggregateEventBase(aggregateId: String) {
    init {
        require(aggregateId.isNotBlank()) { "AggregateEventBase aggregateId is required" }
    }

    open val eventType: String = this::class.simpleName ?: "UnknownEventType"

    var tenantId: String? = null
    var createBy: String? = null
    var createAccountName: String? = null
    var updateBy: String? = null
    var updateAccountName: String? = null
}
