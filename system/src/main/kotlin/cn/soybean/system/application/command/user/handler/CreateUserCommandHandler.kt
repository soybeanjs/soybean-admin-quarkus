package cn.soybean.system.application.command.user.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.user.CreateUserCommand
import cn.soybean.system.application.command.user.toUserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.user.UserAggregate
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateUserCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateUserCommand, Boolean> {
    override fun handle(command: CreateUserCommand): Uni<Boolean> {
        val aggregate = UserAggregate(YitIdHelper.nextId().toString())
        aggregate.createUser(
            command.toUserCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                loginHelper.getTenantId(),
                loginHelper.getUserId(),
                loginHelper.getAccountName()
            )
        )
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateUserCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateUserCommand
}