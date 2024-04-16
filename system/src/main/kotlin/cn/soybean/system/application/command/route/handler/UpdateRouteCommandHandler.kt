package cn.soybean.system.application.command.route.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.route.UpdateRouteCommand
import cn.soybean.system.application.command.route.toRouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.route.RouteAggregate
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UpdateRouteCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<UpdateRouteCommand, Boolean> {
    override fun handle(command: UpdateRouteCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, RouteAggregate::class.java)
            .map { aggregate ->
                aggregate.updateRoute(
                    command.toRouteCreatedOrUpdatedEventBase().also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    }
                )
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "UpdateRouteCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateRouteCommand
}