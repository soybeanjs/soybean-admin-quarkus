package cn.soybean.system.projection.role

import cn.soybean.domain.system.entity.SystemRoleEntity
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
class RoleCreatedProjection(private val roleRepository: SystemRoleRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleCreatedOrUpdatedEventBase::class.java)
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

    override fun supports(eventType: String): Boolean = eventType == RoleCreatedOrUpdatedEventBase.ROLE_CREATED_V1
}
