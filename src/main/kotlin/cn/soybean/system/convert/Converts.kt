package cn.soybean.system.convert

import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.vo.MenuRespVO
import cn.soybean.system.domain.vo.RouteMeta

fun SystemMenuEntity.convertToMenuRespVO(): MenuRespVO {
    val respVO = MenuRespVO(
        parentId = this.parentId,
        name = this.menuName,
        path = this.routePath,
        component = this.component,
        meta = RouteMeta(
            title = this.menuName,
            i18nKey = this.i18nKey,
            roles = this.roles,
            keepAlive = this.keepAlive,
            constant = this.constant,
            icon = this.icon,
            order = this.order,
            href = this.href,
            hideInMenu = this.hideInMenu,
            activeMenu = this.activeMenu,
            multiTab = this.multiTab
        )
    )
    return respVO
}