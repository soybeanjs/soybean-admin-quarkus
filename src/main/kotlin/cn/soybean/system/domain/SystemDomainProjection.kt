package cn.soybean.system.domain

import cn.soybean.domain.EventEntity
import cn.soybean.domain.Projection
import cn.soybean.domain.SerializerUtils
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.event.RoleCreatedOrUpdatedEvent
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEvent
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEvent
import cn.soybean.system.domain.repository.SystemRoleRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleCreatedProjection(private val roleRepository: SystemRoleRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleCreatedOrUpdatedEvent::class.java)
        val systemRoleEntity = SystemRoleEntity(
            name = event.name,
            code = event.code,
            order = event.order,
            status = event.status,
            dataScope = event.dataScope,
            dataScopeDeptIds = event.dataScopeDeptIds,
            remark = event.remark
        ).also {
            it.id = event.aggregateId
            it.tenantId = event.tenantId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }
        return roleRepository.saveOrUpdate(systemRoleEntity).replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RoleCreatedOrUpdatedEvent.ROLE_CREATED_V1
}

@ApplicationScoped
class RoleUpdatedProjection(private val roleRepository: SystemRoleRepository) : Projection {
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleCreatedOrUpdatedEvent::class.java)
        return roleRepository.getById(event.aggregateId)
            .flatMap { role ->
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
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RoleCreatedOrUpdatedEvent.ROLE_UPDATED_V1
}

@ApplicationScoped
class UserCreatedProjection : Projection {
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEvent.USER_CREATED_V1
}

@ApplicationScoped
class UserUpdatedProjection : Projection {
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEvent.USER_UPDATED_V1
}

@ApplicationScoped
class RouteCreatedProjection : Projection {
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == RouteCreatedOrUpdatedEvent.ROUTE_CREATED_V1
}

@ApplicationScoped
class RouteUpdatedProjection : Projection {
    override fun process(eventEntity: EventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == RouteCreatedOrUpdatedEvent.ROUTE_UPDATED_V1
}