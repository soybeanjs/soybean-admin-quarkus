package cn.soybean.system.application.command.tenant.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.tenant.DeleteTenantCommand
import cn.soybean.system.domain.aggregate.tenant.TenantAggregate
import cn.soybean.system.domain.event.tenant.TenantDeletedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteTenantCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<DeleteTenantCommand, Boolean> {
    override fun handle(command: DeleteTenantCommand): Uni<Boolean> = Multi.createFrom().iterable(command.ids)
        .onItem().transformToUniAndMerge { id ->
            eventStoreDB.load(id, TenantAggregate::class.java)
                .map { aggregate ->
                    aggregate.deleteTenant(TenantDeletedEventBase(id).also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    })
                    aggregate
                }
                .flatMap { aggregate -> eventStoreDB.save(aggregate) }
                .onFailure().invoke { ex ->
                    Log.errorf(ex, "Failed to delete tenant with ID: $id")
                }.replaceWithUnit()
        }
        .collect().asList()
        .replaceWith(true)
        .onFailure().invoke { ex -> Log.errorf(ex, "DeleteTenantCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is DeleteTenantCommand
}