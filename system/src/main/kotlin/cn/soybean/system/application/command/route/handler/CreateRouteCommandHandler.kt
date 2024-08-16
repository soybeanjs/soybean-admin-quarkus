package cn.soybean.system.application.command.route.handler

import cn.soybean.domain.system.aggregate.route.RouteAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.route.CreateRouteCommand
import cn.soybean.system.application.command.route.convert.convert2RouteCreatedOrUpdatedEventBase
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRouteCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateRouteCommand, Boolean> {
    override fun handle(command: CreateRouteCommand): Uni<Boolean> {
        val aggregate = RouteAggregate(YitIdHelper.nextId().toString())
        aggregate.createRoute(
            command.convert2RouteCreatedOrUpdatedEventBase(
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
