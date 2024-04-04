package cn.soybean.system.application.command.handler

import cn.soybean.domain.Command
import cn.soybean.domain.CommandHandler
import cn.soybean.domain.EventStoreDB
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.DeleteRoleCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.domain.RoleAggregate
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRoleCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<CreateRoleCommand, Boolean> {
    override fun handle(command: CreateRoleCommand): Uni<Boolean> {
        val aggregate = RoleAggregate(YitIdHelper.nextId().toString())
        aggregate.createRole(command)
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.debugf(ex, "CreateRoleCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateRoleCommand
}

@ApplicationScoped
class UpdateRoleCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<UpdateRoleCommand, Boolean> {
    override fun handle(command: UpdateRoleCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, RoleAggregate::class.java)
            .map { aggregate ->
                aggregate.updateRole(command)
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.debugf(ex, "UpdateRoleCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateRoleCommand
}

@ApplicationScoped
class DeleteRoleCommandHandler : CommandHandler<DeleteRoleCommand, Boolean> {
    override fun handle(command: DeleteRoleCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRoleCommand
}