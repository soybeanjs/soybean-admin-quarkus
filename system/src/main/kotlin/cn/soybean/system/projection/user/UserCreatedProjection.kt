/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.user

import cn.soybean.domain.system.entity.SystemUserEntity
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
class UserCreatedProjection(
    private val userRepository: SystemUserRepository,
) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, UserCreatedOrUpdatedEventBase::class.java)
        val systemUserEntity =
            SystemUserEntity(
                accountName = event.accountName,
                accountPassword = event.accountPassword,
                nickName = event.nickName,
                personalProfile = event.personalProfile,
                email = event.email,
                countryCode = event.countryCode,
                phoneCode = event.phoneCode,
                phoneNumber = event.phoneNumber,
                gender = event.gender,
                avatar = event.phoneNumber,
                deptId = event.deptId,
                status = event.status,
            ).also {
                it.id = event.aggregateId
                it.tenantId = event.tenantId
                it.createBy = event.createBy
                it.createAccountName = event.createAccountName
            }
        return userRepository.saveOrUpdate(systemUserEntity).replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_CREATED_V1
}
