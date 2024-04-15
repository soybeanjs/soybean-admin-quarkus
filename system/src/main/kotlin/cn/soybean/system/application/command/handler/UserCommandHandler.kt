package cn.soybean.system.application.command.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import cn.soybean.system.application.command.toUserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.aggregate.UserAggregate
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

@ApplicationScoped
class DeleteUserCommandHandler : CommandHandler<DeleteUserCommand, Boolean> {
    override fun handle(command: DeleteUserCommand): Uni<Boolean> {
        TODO("Not yet implemented")
    }

    override fun canHandle(command: Command): Boolean = command is DeleteUserCommand
}