/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.route.handler

import cn.soybean.domain.system.aggregate.route.RouteAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.route.UpdateRouteCommand
import cn.soybean.system.application.command.route.toRouteCreatedOrUpdatedEventBase
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UpdateRouteCommandHandler(
    private val eventStoreDB: EventStoreDB,
    private val loginHelper: LoginHelper,
) : CommandHandler<UpdateRouteCommand, Boolean> {
    override fun handle(command: UpdateRouteCommand): Uni<Boolean> =
        eventStoreDB
            .load(command.id, RouteAggregate::class.java)
            .map { aggregate ->
                aggregate.updateRoute(
                    command.toRouteCreatedOrUpdatedEventBase().also {
                        it.tenantId = loginHelper.getTenantId()
                        it.updateBy = loginHelper.getUserId()
                        it.updateAccountName = loginHelper.getAccountName()
                    },
                )
                aggregate
            }.flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .replaceWith(true)
            .onFailure()
            .invoke { ex -> Log.errorf(ex, "UpdateRouteCommandHandler fail") }

    override fun canHandle(command: Command): Boolean = command is UpdateRouteCommand
}
