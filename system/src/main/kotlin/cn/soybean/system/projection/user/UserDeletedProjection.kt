/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.user

import cn.soybean.domain.system.event.user.UserDeletedEventBase
import cn.soybean.domain.system.repository.SystemRoleUserRepository
import cn.soybean.domain.system.repository.SystemUserRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserDeletedProjection(
    private val userRepository: SystemUserRepository,
    private val roleUserRepository: SystemRoleUserRepository,
) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, UserDeletedEventBase::class.java)
        return event.tenantId?.let { tenantId ->
            userRepository
                .delById(event.aggregateId, tenantId)
                .flatMap { roleUserRepository.delByUserId(event.aggregateId, tenantId) }
                .replaceWithUnit()
        } ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == UserDeletedEventBase.USER_DELETED_V1
}
