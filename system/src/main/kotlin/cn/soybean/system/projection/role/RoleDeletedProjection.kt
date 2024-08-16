package cn.soybean.system.projection.role

import cn.soybean.domain.system.event.role.RoleDeletedEventBase
import cn.soybean.domain.system.repository.SystemRoleMenuRepository
import cn.soybean.domain.system.repository.SystemRoleRepository
import cn.soybean.domain.system.repository.SystemRoleUserRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleDeletedProjection(
    private val roleRepository: SystemRoleRepository,
    private val roleMenuRepository: SystemRoleMenuRepository,
    private val roleUserRepository: SystemRoleUserRepository
) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleDeletedEventBase::class.java)
        return event.tenantId?.let { tenantId ->
            roleRepository.delById(event.aggregateId, tenantId)
                .flatMap { roleMenuRepository.delByRoleId(event.aggregateId, tenantId) }
                .flatMap { roleUserRepository.delByRoleId(event.aggregateId, tenantId) }
                .replaceWithUnit()
        } ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == RoleDeletedEventBase.ROLE_DELETED_V1
}
