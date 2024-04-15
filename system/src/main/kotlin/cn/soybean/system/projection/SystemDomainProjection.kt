package cn.soybean.system.projection

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.event.RoleCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.RoleDeletedEventBase
import cn.soybean.system.domain.event.RouteCreatedOrUpdatedEventBase
import cn.soybean.system.domain.event.RouteDeletedEventBase
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.repository.SystemMenuRepository
import cn.soybean.system.domain.repository.SystemRoleMenuRepository
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.domain.repository.SystemRoleUserRepository
import cn.soybean.system.domain.repository.SystemUserRepository
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

@ApplicationScoped
class UserCreatedProjection(private val userRepository: SystemUserRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, UserCreatedOrUpdatedEventBase::class.java)
        val systemUserEntity = SystemUserEntity(
            accountName = event.accountName,
            accountPassword = event.accountPassword,
            nickName = event.nickName,
            personalProfile = event.personalProfile,
            email = event.email,
            countryCode = event.countryCode,
            phoneCode = event.phoneCode,
            phoneNumber = event.phoneNumber,
            gender = event.gender,
            avatar = event.phoneNumber,
            deptId = event.deptId,
            status = event.status
        ).also {
            it.id = event.aggregateId
            it.tenantId = event.tenantId
            it.createBy = event.createBy
            it.createAccountName = event.createAccountName
        }
        return userRepository.saveOrUpdate(systemUserEntity).replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_CREATED_V1
}

@ApplicationScoped
class UserUpdatedProjection(private val userRepository: SystemUserRepository) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, UserCreatedOrUpdatedEventBase::class.java)
        return event.tenantId?.let { tenantId ->
            userRepository.getById(event.aggregateId, tenantId)
                .flatMap { user ->
                    user?.let {
                        user.also {
                            user.accountName = event.accountName
                            user.accountPassword = event.accountPassword
                            user.nickName = event.nickName
                            user.personalProfile = event.personalProfile
                            user.email = event.email
                            user.countryCode = event.countryCode
                            user.phoneCode = event.phoneCode
                            user.phoneNumber = event.phoneNumber
                            user.gender = event.gender
                            user.avatar = event.phoneNumber
                            user.deptId = event.deptId
                            user.status = event.status
                            user.updateBy = event.updateBy
                            user.updateAccountName = event.updateAccountName
                        }
                        userRepository.saveOrUpdate(user)
                    }
                }.replaceWithUnit()
        } ?: Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == UserCreatedOrUpdatedEventBase.USER_UPDATED_V1
}

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

@ApplicationScoped
class RouteDeletedProjection(
    private val menuRepository: SystemMenuRepository,
    private val roleMenuRepository: SystemRoleMenuRepository
) : Projection {

    @WithTransaction
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        val event = SerializerUtils.deserializeFromJsonBytes(eventEntity.data, RouteDeletedEventBase::class.java)
        return menuRepository.delById(event.aggregateId)
            .flatMap { event.tenantId?.let { tenantId -> roleMenuRepository.delByRoleId(event.aggregateId, tenantId) } }
            .replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == RouteDeletedEventBase.ROUTE_DELETED_V1
}