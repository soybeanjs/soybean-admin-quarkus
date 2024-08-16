package cn.soybean.system.projection.route

import cn.soybean.domain.system.event.route.RouteCreatedOrUpdatedEventBase
import cn.soybean.domain.system.repository.SystemMenuRepository
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteUpdatedProjection(private val menuRepository: SystemMenuRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RouteCreatedOrUpdatedEventBase::class.java)
        return menuRepository.getById(event.aggregateId)
            .flatMap { menu ->
                menu.also {
                    menu.menuName = event.menuName
                    menu.menuType = event.menuType
                    menu.order = event.order
                    menu.parentId = event.parentId
                    menu.icon = event.icon
                    menu.iconType = event.iconType
                    menu.routeName = event.routeName
                    menu.routePath = event.routePath
                    menu.component = event.component
                    menu.i18nKey = event.i18nKey
                    menu.multiTab = event.multiTab
                    menu.activeMenu = event.activeMenu
                    menu.hideInMenu = event.hideInMenu
                    menu.status = event.status
                    menu.keepAlive = event.keepAlive
                    menu.constant = event.constant
                    menu.href = event.href
                    menu.updateBy = event.updateBy
                    menu.updateAccountName = event.updateAccountName
                }
                menuRepository.saveOrUpdate(menu)
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RouteCreatedOrUpdatedEventBase.ROUTE_UPDATED_V1
}

