package cn.soybean.system.application.command.user.handler

import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.user.DeleteUserCommand
import cn.soybean.system.domain.aggregate.user.UserAggregate
import cn.soybean.system.domain.event.user.UserDeletedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteUserCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<DeleteUserCommand, Boolean> {
    override fun handle(command: DeleteUserCommand): Uni<Boolean> = Multi.createFrom().iterable(command.ids)
        .onItem().transformToUniAndMerge { id ->
            eventStoreDB.load(id, UserAggregate::class.java)
                .map { aggregate ->
                    aggregate.deleteUser(UserDeletedEventBase(id).also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    })
                    aggregate
                }
                .flatMap { aggregate -> eventStoreDB.save(aggregate) }
                .onFailure().invoke { ex ->
                    Log.errorf(ex, "Failed to delete user with ID: $id")
                }.replaceWithUnit()
        }
        .collect().asList()
        .replaceWith(true)
        .onFailure().invoke { ex -> Log.errorf(ex, "DeleteUserCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is DeleteUserCommand
}