/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.aggregate.route

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.event.route.RouteCreatedOrUpdatedEventBase
import cn.soybean.domain.system.event.route.RouteDeletedEventBase
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.util.SerializerUtils
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RouteAggregate
@JsonCreator
constructor(
    @JsonProperty("aggregateId") aggregateId: String,
) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {
    private var menuName: String? = null
    private var menuType: DbEnums.MenuItemType? = null
    private var order: Int? = null
    private var parentId: String? = null
    private var icon: String? = null
    private var iconType: String? = null
    private var routeName: String? = null
    private var routePath: String? = null
    private var component: String? = null
    private var i18nKey: String? = null
    private var multiTab: Boolean? = null
    private var activeMenu: String? = null
    private var hideInMenu: Boolean? = null
    private var status: DbEnums.Status? = null
    private var roles: List<String>? = null
    private var keepAlive: Boolean? = null
    private var constant: Boolean? = null
    private var href: String? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            RouteCreatedOrUpdatedEventBase.ROUTE_CREATED_V1, RouteCreatedOrUpdatedEventBase.ROUTE_UPDATED_V1 ->
                handle(
                    SerializerUtils.deserializeFromJsonBytes(
                        eventEntity.data,
                        RouteCreatedOrUpdatedEventBase::class.java,
                    ),
                )

            RouteDeletedEventBase.ROUTE_DELETED_V1 ->
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    RouteDeletedEventBase::class.java,
                )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: RouteCreatedOrUpdatedEventBase) {
        this.menuName = event.menuName
        this.menuType = event.menuType
        this.order = event.order
        this.parentId = event.parentId
        this.icon = event.icon
        this.iconType = event.iconType
        this.routeName = event.routeName
        this.routePath = event.routePath
        this.component = event.component
        this.i18nKey = event.i18nKey
        this.multiTab = event.multiTab
        this.activeMenu = event.activeMenu
        this.hideInMenu = event.hideInMenu
        this.status = event.status
        this.roles = event.roles
        this.keepAlive = event.keepAlive
        this.constant = event.constant
        this.href = event.href
    }

    fun createRoute(data: RouteCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEventBase.ROUTE_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateRoute(data: RouteCreatedOrUpdatedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteCreatedOrUpdatedEventBase.ROUTE_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun deleteRoute(data: RouteDeletedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(RouteDeletedEventBase.ROUTE_DELETED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "RouteAggregate"
    }
}
