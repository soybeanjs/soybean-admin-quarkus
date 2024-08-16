package cn.soybean.system.application.command.user.handler

import cn.soybean.domain.system.aggregate.user.UserAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.user.UpdateUserCommand
import cn.soybean.system.application.command.user.toUserCreatedOrUpdatedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UpdateUserCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<UpdateUserCommand, Boolean> {
    override fun handle(command: UpdateUserCommand): Uni<Boolean> =
        eventStoreDB.load(command.id, UserAggregate::class.java)
            .map { aggregate ->
                aggregate.updateUser(
                    command.toUserCreatedOrUpdatedEventBase().also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    }
                )
                aggregate
            }
            .flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "UpdateUserCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateUserCommand
}
