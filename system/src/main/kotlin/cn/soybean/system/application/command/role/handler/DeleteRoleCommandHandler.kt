package cn.soybean.system.application.command.role.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.role.DeleteRoleCommand
import cn.soybean.system.domain.aggregate.role.RoleAggregate
import cn.soybean.system.domain.event.role.RoleDeletedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteRoleCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<DeleteRoleCommand, Boolean> {
    override fun handle(command: DeleteRoleCommand): Uni<Boolean> = Multi.createFrom().iterable(command.ids)
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

    override fun canHandle(command: Command): Boolean = command is DeleteRoleCommand
}