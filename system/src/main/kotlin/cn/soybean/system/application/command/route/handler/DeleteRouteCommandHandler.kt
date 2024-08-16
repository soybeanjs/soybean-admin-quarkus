package cn.soybean.system.application.command.route.handler

import cn.soybean.domain.system.aggregate.route.RouteAggregate
import cn.soybean.domain.system.event.route.RouteDeletedEventBase
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.route.DeleteRouteCommand
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteRouteCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<DeleteRouteCommand, Boolean> {
    override fun handle(command: DeleteRouteCommand): Uni<Boolean> = Multi.createFrom().iterable(command.ids)
        .onItem().transformToUniAndMerge { id ->
            eventStoreDB.load(id, RouteAggregate::class.java)
                .map { aggregate ->
                    aggregate.deleteRoute(RouteDeletedEventBase(id).also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    })
                    aggregate
                }
                .flatMap { aggregate -> eventStoreDB.save(aggregate) }
                .onFailure().invoke { ex ->
                    Log.errorf(ex, "Failed to delete route with ID: $id")
                }.replaceWithUnit()
        }
        .collect().asList()
        .replaceWith(true)
        .onFailure().invoke { ex -> Log.errorf(ex, "DeleteRouteCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is DeleteRouteCommand
}
