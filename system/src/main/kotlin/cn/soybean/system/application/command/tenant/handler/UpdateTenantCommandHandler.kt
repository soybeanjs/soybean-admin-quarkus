package cn.soybean.system.application.command.tenant.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.tenant.UpdateTenantCommand
import cn.soybean.system.application.command.tenant.toTenantUpdatedEventBase
import cn.soybean.system.domain.aggregate.tenant.TenantAggregate
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UpdateTenantCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<UpdateTenantCommand, Boolean> {
    override fun handle(command: UpdateTenantCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, TenantAggregate::class.java)
            .map { aggregate ->
                aggregate.updateTenant(
                    command.toTenantUpdatedEventBase().also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    }
                )
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "UpdateTenantCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateTenantCommand
}