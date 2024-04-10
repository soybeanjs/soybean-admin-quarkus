package cn.soybean.system.application.command.handler

import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.DeleteRouteCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.domain.RouteAggregate
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRouteCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<CreateRouteCommand, Boolean> {
    override fun handle(command: CreateRouteCommand): Uni<Boolean> {
        val aggregate = RouteAggregate(YitIdHelper.nextId().toString())
        aggregate.createRoute(command)
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateRouteCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateRouteCommand
}

@ApplicationScoped
class UpdateRouteCommandHandler(private val eventStoreDB: EventStoreDB) : CommandHandler<UpdateRouteCommand, Boolean> {
    override fun handle(command: UpdateRouteCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, RouteAggregate::class.java)
            .map { aggregate ->
                aggregate.updateRoute(command)
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "UpdateRouteCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateRouteCommand
}

@ApplicationScoped
class DeleteRouteCommandHandler : CommandHandler<DeleteRouteCommand, Boolean> {
    override fun handle(command: DeleteRouteCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteRouteCommand
}