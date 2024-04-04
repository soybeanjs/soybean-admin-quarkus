package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.domain.EventStoreDB
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import cn.soybean.system.domain.UserAggregate
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateUserCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<CreateUserCommand, Boolean> {
    override fun handle(command: CreateUserCommand): Uni<Boolean> {
        val aggregate = UserAggregate(YitIdHelper.nextId().toString())
        aggregate.createUser(command)
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.debugf(ex, "CreateUserCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateUserCommand
}

@ApplicationScoped
class UpdateUserCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<UpdateUserCommand, Boolean> {
    override fun handle(command: UpdateUserCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, UserAggregate::class.java)
            .map { aggregate ->
                aggregate.updateUser(command)
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.debugf(ex, "UpdateUserCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateUserCommand
}

@ApplicationScoped
class DeleteUserCommandHandler : CommandHandler<DeleteUserCommand, Boolean> {
    override fun handle(command: DeleteUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteUserCommand
}