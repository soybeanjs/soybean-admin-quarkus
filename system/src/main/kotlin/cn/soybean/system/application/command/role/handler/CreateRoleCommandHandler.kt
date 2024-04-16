package cn.soybean.system.application.command.role.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.role.CreateRoleCommand
import cn.soybean.system.application.command.role.toRoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.role.RoleAggregate
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateRoleCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateRoleCommand, Boolean> {
    override fun handle(command: CreateRoleCommand): Uni<Boolean> {
        val aggregate = RoleAggregate(YitIdHelper.nextId().toString())
        aggregate.createRole(
            command.toRoleCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                loginHelper.getTenantId(),
                loginHelper.getUserId(),
                loginHelper.getAccountName()
            )
        )
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateRoleCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateRoleCommand
}