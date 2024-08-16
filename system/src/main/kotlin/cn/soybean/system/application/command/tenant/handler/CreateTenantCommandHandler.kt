package cn.soybean.system.application.command.tenant.handler

import cn.soybean.domain.system.aggregate.tenant.TenantAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.tenant.CreateTenantCommand
import cn.soybean.system.application.command.tenant.convert.convert2TenantCreatedOrUpdatedEventBase
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateTenantCommandHandler(private val eventStoreDB: EventStoreDB, private val loginHelper: LoginHelper) :
    CommandHandler<CreateTenantCommand, TenantAggregate> {
    override fun handle(command: CreateTenantCommand): Uni<TenantAggregate> {
        val aggregate = TenantAggregate(YitIdHelper.nextId().toString())
        aggregate.createTenant(
            command.convert2TenantCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                loginHelper.getTenantId(),
                loginHelper.getUserId(),
                loginHelper.getAccountName(),
                YitIdHelper.nextId().toString()
            )
        )
        return eventStoreDB.save(aggregate).replaceWith(aggregate)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateTenantCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateTenantCommand
}
