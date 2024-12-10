/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.user

import cn.soybean.domain.system.event.user.UserCreatedOrUpdatedEventBase
import cn.soybean.domain.system.repository.SystemUserRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserUpdatedProjection(private val userRepository: SystemUserRepository) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, UserCreatedOrUpdatedEventBase::class.java)
        return event.tenantId?.let { tenantId ->
            userRepository.getById(event.aggregateId, tenantId)
                .flatMap { user ->
                    user?.let {
                        user.also {
                            user.accountName = event.accountName
                            user.accountPassword = event.accountPassword
                            user.nickName = event.nickName
                            user.personalProfile = event.personalProfile
                            user.email = event.email
                            user.countryCode = event.countryCode
                            user.phoneCode = event.phoneCode
                            user.phoneNumber = event.phoneNumber
                            user.gender = event.gender
                            user.avatar = event.phoneNumber
                            user.deptId = event.deptId
                            user.status = event.status
                            user.updateBy = event.updateBy
                            user.updateAccountName = event.updateAccountName
                        }
                        userRepository.saveOrUpdate(user)
                    }
                }.replaceWithUnit()
        } ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_UPDATED_V1
}
