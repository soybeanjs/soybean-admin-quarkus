package cn.soybean.system.projection.route

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.event.route.RouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.repository.SystemMenuRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteCreatedProjection(private val menuRepository: SystemMenuRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RouteCreatedOrUpdatedEventBase::class.java)
        val systemMenuEntity = SystemMenuEntity(
            menuName = event.menuName,
            menuType = event.menuType,
            order = event.order,
            parentId = event.parentId,
            icon = event.icon,
            iconType = event.iconType,
            routeName = event.routeName,
            routePath = event.routePath,
            component = event.component,
            i18nKey = event.i18nKey,
            multiTab = event.multiTab,
            activeMenu = event.activeMenu,
            hideInMenu = event.hideInMenu,
            status = event.status,
            keepAlive = event.keepAlive,
            constant = event.constant,
            href = event.href
        ).also {
            it.id = event.aggregateId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }
        return menuRepository.saveOrUpdate(systemMenuEntity).replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RouteCreatedOrUpdatedEventBase.ROUTE_CREATED_V1
}