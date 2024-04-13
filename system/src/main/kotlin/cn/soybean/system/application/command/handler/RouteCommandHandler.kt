package cn.soybean.system.application.command.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.DeleteRouteCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.application.command.toRouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.RouteAggregate
import cn.soybean.system.domain.event.RouteDeletedEventBase
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRouteCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateRouteCommand, Boolean> {
    override fun handle(command: CreateRouteCommand): Uni<Boolean> {
        val aggregate = RouteAggregate(YitIdHelper.nextId().toString())
        aggregate.createRoute(
            command.toRouteCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                loginHelper.getTenantId(),
                loginHelper.getUserId(),
                loginHelper.getAccountName()
            )
        )
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateRouteCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateRouteCommand
}

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