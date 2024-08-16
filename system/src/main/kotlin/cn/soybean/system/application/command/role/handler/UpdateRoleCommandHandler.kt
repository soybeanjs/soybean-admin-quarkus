package cn.soybean.system.application.command.role.handler

import cn.soybean.domain.system.aggregate.role.RoleAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.role.UpdateRoleCommand
import cn.soybean.system.application.command.role.toRoleCreatedOrUpdatedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

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
