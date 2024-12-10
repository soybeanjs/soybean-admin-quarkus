/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.application.command

import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance

@ApplicationScoped
class CommandInvoker(private val handlers: Instance<CommandHandler<*, *>>) {
    @Suppress("UNCHECKED_CAST")
    fun <C : Command, R> dispatch(command: C): Uni<R> {
        val handler =
            handlers
                .stream()
                .filter { it.canHandle(command) }
                .findFirst()
                .orElseThrow { IllegalStateException("No handler found for command: ${command::class.simpleName}") }
                .let { it as CommandHandler<C, R> }

        return handler.handle(command)
    }
}
