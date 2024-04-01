package cn.soybean.domain

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance

interface Command

interface CommandHandler<C : Command, R> {
    fun handle(command: C): Uni<R>
    fun canHandle(command: Command): Boolean
}

@ApplicationScoped
class CommandInvoker(private val handlers: Instance<CommandHandler<*, *>>) {

    @Suppress("UNCHECKED_CAST")
    fun <C : Command, R> dispatch(command: C): Uni<R> {
        val handler = handlers
            .stream()
            .filter { it.canHandle(command) }
            .findFirst()
            .orElseThrow { IllegalStateException("No handler found for command: ${command::class.simpleName}") }
            .let { it as CommandHandler<C, R> }

        return handler.handle(command)
    }
}