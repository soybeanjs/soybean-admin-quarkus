/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.route.handler

import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.entity.SystemMenuEntity
import cn.soybean.domain.system.repository.SystemMenuRepository
import cn.soybean.domain.system.repository.SystemTenantRepository
import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.application.convert.convertToMenuResponse
import cn.soybean.system.application.query.route.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdAndConstantQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.route.RouteByConstantQuery
import cn.soybean.system.application.query.route.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.route.RouteByIdQuery
import cn.soybean.system.application.query.route.RouteByTenantIdQuery
import cn.soybean.system.application.query.route.service.RouteQueryService
import cn.soybean.system.interfaces.rest.dto.response.route.MenuResponse
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

fun SystemMenuEntity.toMenuResponse(): MenuResponse =
    MenuResponse(
        id = id,
        menuName = menuName,
        menuType = menuType,
        order = order,
        parentId = parentId,
        icon = icon,
        iconType = iconType,
        routeName = routeName,
        routePath = routePath,
        component = component,
        i18nKey = i18nKey,
        multiTab = multiTab,
        activeMenu = activeMenu,
        hideInMenu = hideInMenu,
        status = status,
        roles = roles,
        keepAlive = keepAlive,
        constant = constant,
        href = href,
    )

@ApplicationScoped
class RouteQueryHandler(
    private val systemMenuRepository: SystemMenuRepository,
    private val systemTenantRepository: SystemTenantRepository,
) : RouteQueryService {
    override fun handle(query: GetRoutesByUserIdQuery): Uni<Map<String, Any>> {
        val (userId) = query
        val menusUni: Uni<List<SystemMenuEntity>> = getRoutesByUserId(userId)

        return menusUni.map { menuItems ->
            val menuRoutes =
                buildTree(
                    items = menuItems,
                    idSelector = { it.id },
                    parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                    rootId = DbConstants.PARENT_ID_ROOT,
                    orderSelector = { it.order ?: 0 },
                    transform = { item, children ->
                        item.convertToMenuResponse().apply { this.children = children }
                    },
                )
            mapOf("routes" to menuRoutes, "home" to "home")
        }
    }

    override fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuResponse>> {
        val (userId, tenantId) = query
        val menusUni: Uni<List<SystemMenuEntity>> = listRoute(userId, tenantId)

        return menusUni.map { menuItems ->
            buildTree(
                items = menuItems,
                idSelector = { it.id },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.toMenuResponse().apply { this.children = children }
                },
            )
        }
    }

    override fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity> = systemMenuRepository.getById(query.id)

    override fun handle(query: RouteByIdBuiltInQuery): Uni<Boolean> = systemMenuRepository.getById(query.id).map { it?.builtIn != false }

    override fun handle(query: RouteByConstantQuery): Uni<List<MenuRoute>> =
        systemMenuRepository
            .findAllByConstant(query.constant)
            .map { menuList -> menuList.map { it.convertToMenuResponse() } }

    override fun handle(query: RouteByTenantIdQuery): Uni<Set<String>> =
        when (query.tenantId) {
            DbConstants.SUPER_TENANT -> systemMenuRepository.all().map { menus -> menus.map { it.id }.toSet() }
            else -> systemTenantRepository.getById(query.tenantId).map { it.menuIds }
        }

    override fun handle(query: ListTreeRoutesByUserIdAndConstantQuery): Uni<List<MenuResponse>> {
        val (userId, tenantId, constant) = query
        val menusUni: Uni<List<SystemMenuEntity>> = listRouteByConstant(userId, tenantId, constant)

        return menusUni.map { menuItems ->
            buildTree(
                items = menuItems,
                idSelector = { it.id },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.toMenuResponse().apply { this.children = children }
                },
            )
        }
    }

    private fun getRoutesByUserId(userId: String): Uni<List<SystemMenuEntity>> =
        when {
            isSuperUser(userId) -> systemMenuRepository.all()
            else -> systemMenuRepository.allByUserId(userId)
        }

    private fun listRoute(
        userId: String,
        tenantId: String,
    ): Uni<List<SystemMenuEntity>> =
        when {
            isSuperUser(userId) -> systemMenuRepository.all()
            else -> systemMenuRepository.allByTenantId(tenantId)
        }

    private fun listRouteByConstant(
        userId: String,
        tenantId: String,
        constant: Boolean,
    ): Uni<List<SystemMenuEntity>> =
        when {
            isSuperUser(userId) -> systemMenuRepository.findAllByConstant(constant)
            else -> systemMenuRepository.allByTenantIdAndConstant(tenantId, constant)
        }

    private fun <T, R> buildTree(
        items: List<T>,
        idSelector: (T) -> String,
        parentIdSelector: (T) -> String,
        rootId: String,
        orderSelector: (T) -> Int,
        transform: (T, children: List<R>) -> R,
    ): List<R> {
        val childrenByParentId = items.groupBy(parentIdSelector)

        fun buildNode(item: T): R {
            val children =
                childrenByParentId[idSelector(item)]
                    ?.sortedBy(orderSelector)
                    ?.map(::buildNode)
                    .orEmpty()
            return transform(item, children)
        }

        return childrenByParentId[rootId]
            ?.sortedBy(orderSelector)
            ?.map(::buildNode)
            .orEmpty()
    }
}
