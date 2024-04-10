package cn.soybean.shared.application.command

import io.smallrye.mutiny.Uni

interface CommandHandler<C : Command, R> {
    fun handle(command: C): Uni<R>
    fun canHandle(command: Command): Boolean
}