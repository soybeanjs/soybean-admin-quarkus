package cn.soybean.system.application.command.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.DeleteRoleCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.application.command.toRoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.RoleAggregate
import cn.soybean.system.domain.event.RoleDeletedEventBase
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRoleCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateRoleCommand, Boolean> {
    override fun handle(command: CreateRoleCommand): Uni<Boolean> {
        val aggregate = RoleAggregate(YitIdHelper.nextId().toString())
        aggregate.createRole(command, loginHelper.getTenantId(), loginHelper.getUserId(), loginHelper.getAccountName())
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateRoleCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateRoleCommand
}

@ApplicationScoped
class UpdateRoleCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<UpdateRoleCommand, Boolean> {
    override fun handle(command: UpdateRoleCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, RoleAggregate::class.java)
            .map { aggregate ->
                aggregate.updateRole(
                    command.toRoleCreatedOrUpdatedEventBase().also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    }
                )
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "UpdateRoleCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateRoleCommand
}

@ApplicationScoped
class DeleteRoleCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<DeleteRoleCommand, Boolean> {
    override fun handle(command: DeleteRoleCommand): Uni<Boolean> {
        return Multi.createFrom().iterable(command.ids)
            .onItem().transformToUniAndMerge { id ->
                eventStoreDB.load(id, RoleAggregate::class.java)
                    .map { aggregate ->
                        aggregate.deleteRole(RoleDeletedEventBase(id).also {
                            it.tenantId = loginHelper.getTenantId()
                            it.updateBy = loginHelper.getUserId()
                            it.updateAccountName = loginHelper.getAccountName()
                        })
                        aggregate
                    }
                    .flatMap { aggregate -> eventStoreDB.save(aggregate) }
                    .onFailure().invoke { ex ->
                        Log.errorf(ex, "Failed to delete role with ID: $id")
                    }.replaceWithUnit()
            }
            .collect().asList()
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "DeleteRoleCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRoleCommand
}