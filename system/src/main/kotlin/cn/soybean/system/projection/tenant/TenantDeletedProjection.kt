/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.tenant

import cn.soybean.domain.event.EventInvoker
import cn.soybean.domain.system.event.tenant.TenantDeletedEventBase
import cn.soybean.domain.system.repository.SystemTenantRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantDeletedProjection(
    private val eventInvoker: EventInvoker,
    private val tenantRepository: SystemTenantRepository,
) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, TenantDeletedEventBase::class.java)
        return tenantRepository.getById(event.aggregateId)
            .flatMap { tenant ->
                tenant.also {
                    tenant.status = event.status
                }
                tenantRepository.saveOrUpdate(tenant)
            }
            .map {
                eventInvoker.distributionProcess(event)
            }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == TenantDeletedEventBase.TENANT_DELETED_V1
}
