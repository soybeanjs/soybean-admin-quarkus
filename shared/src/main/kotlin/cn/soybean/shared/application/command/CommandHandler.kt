/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.application.command

import io.smallrye.mutiny.Uni

interface CommandHandler<C : Command, R> {
    fun handle(command: C): Uni<R>

    fun canHandle(command: Command): Boolean
}
