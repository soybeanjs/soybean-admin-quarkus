package cn.soybean.system.application.command.user.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.user.TenantAssociatesUserCommand
import cn.soybean.system.application.command.user.convert.convert2UserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.user.UserAggregate
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantAssociatesUserCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<TenantAssociatesUserCommand, Boolean> {
    override fun handle(command: TenantAssociatesUserCommand): Uni<Boolean> {
        val aggregate = UserAggregate(command.contactUserId)
        aggregate.createUser(
            command.convert2UserCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                command.tenantId,
                command.contactAccountName,
                loginHelper.getUserId(),
                loginHelper.getAccountName()
            )
        )
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "TenantAssociatesUserCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is TenantAssociatesUserCommand
}