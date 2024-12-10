/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.user.handler

import cn.soybean.domain.system.aggregate.user.UserAggregate
import cn.soybean.infrastructure.security.LoginHelper
import cn.soybean.shared.application.command.Command
import cn.soybean.shared.application.command.CommandHandler
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.system.application.command.user.CreateUserCommand
import cn.soybean.system.application.command.user.convert.convert2UserCreatedOrUpdatedEventBase
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
            command.convert2UserCreatedOrUpdatedEventBase(
                aggregate.aggregateId,
                loginHelper.getTenantId(),
                loginHelper.getUserId(),
                loginHelper.getAccountName(),
            ),
        )
        return eventStoreDB.save(aggregate).replaceWith(true)
            .onFailure().invoke { ex -> Log.errorf(ex, "CreateUserCommandHandler fail") }
    }

    override fun canHandle(command: Command): Boolean = command is CreateUserCommand
}
