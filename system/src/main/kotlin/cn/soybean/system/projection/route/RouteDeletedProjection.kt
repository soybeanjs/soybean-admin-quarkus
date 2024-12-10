/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.projection.route

import cn.soybean.domain.system.event.route.RouteDeletedEventBase
import cn.soybean.domain.system.repository.SystemMenuRepository
import cn.soybean.domain.system.repository.SystemRoleMenuRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteDeletedProjection(
    private val menuRepository: SystemMenuRepository,
    private val roleMenuRepository: SystemRoleMenuRepository,
) : Projection {
    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RouteDeletedEventBase::class.java)
        return menuRepository.delById(event.aggregateId)
            .flatMap { event.tenantId?.let { tenantId -> roleMenuRepository.delByMenuId(event.aggregateId, tenantId) } }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RouteDeletedEventBase.ROUTE_DELETED_V1
}
