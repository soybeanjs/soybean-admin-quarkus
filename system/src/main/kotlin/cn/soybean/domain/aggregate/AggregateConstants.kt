/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.aggregate

object AggregateConstants {
    const val EVENT: String = "event"
    const val SNAPSHOT: String = "snapshot"

    const val AGGREGATE_ID: String = "aggregate_id"
    const val AGGREGATE_TYPE: String = "aggregate_type"
    const val AGGREGATE_VERSION: String = "aggregate_version"
    const val EVENT_TYPE: String = "event_type"
    const val DATA: String = "data"
    const val METADATA: String = "metadata"
    const val TIMESTAMP: String = "timestamp"
}
