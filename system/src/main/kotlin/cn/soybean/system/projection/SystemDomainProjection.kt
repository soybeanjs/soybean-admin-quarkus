package cn.soybean.system.projection

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.event.RoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.RoleDeletedEventBase
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.repository.SystemMenuRepository
import cn.soybean.system.domain.repository.SystemRoleRepository
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

@ApplicationScoped
class RoleUpdatedProjection(private val roleRepository: SystemRoleRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleCreatedOrUpdatedEventBase::class.java)
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

    override fun supports(eventType: String): Boolean = eventType == RoleCreatedOrUpdatedEventBase.ROLE_UPDATED_V1
}

@ApplicationScoped
class RoleDeletedProjection(private val roleRepository: SystemRoleRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RoleDeletedEventBase::class.java)
        return event.tenantId?.let { roleRepository.delById(event.aggregateId, it).replaceWithUnit() }
            ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == RoleDeletedEventBase.ROLE_DELETED_V1
}

@ApplicationScoped
class UserCreatedProjection : Projection {
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_CREATED_V1
}

@ApplicationScoped
class UserUpdatedProjection : Projection {
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        TODO("Not yet implemented")
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_UPDATED_V1
}

@ApplicationScoped
class RouteCreatedProjection(private val menuRepository: SystemMenuRepository) : Projection {
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

@ApplicationScoped
class RouteUpdatedProjection(private val menuRepository: SystemMenuRepository) : Projection {
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