/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.application.query

import io.smallrye.mutiny.Uni

interface QueryHandler<C : Query, R> {
    fun handle(query: C): Uni<R>

    fun canHandle(query: Query): Boolean
}
