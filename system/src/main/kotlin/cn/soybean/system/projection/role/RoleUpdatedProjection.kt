/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.role

import cn.soybean.domain.system.event.role.RoleCreatedOrUpdatedEventBase
import cn.soybean.domain.system.repository.SystemRoleRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleUpdatedProjection(private val roleRepository: SystemRoleRepository) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleCreatedOrUpdatedEventBase::class.java)
        return event.tenantId?.let { tenantId ->
            roleRepository.getById(event.aggregateId, tenantId)
                .flatMap { role ->
                    role?.let {
                        role.also {
                            role.name = event.name
                            role.code = event.code
                            role.order = event.order
                            role.status = event.status
                            role.dataScope = event.dataScope
                            role.dataScopeDeptIds = event.dataScopeDeptIds
                            role.remark = event.remark
                            role.updateBy = event.updateBy
                            role.updateAccountName = event.updateAccountName
                        }
                        roleRepository.saveOrUpdate(role)
                    }
                }.replaceWithUnit()
        } ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == RoleCreatedOrUpdatedEventBase.ROLE_UPDATED_V1
}
