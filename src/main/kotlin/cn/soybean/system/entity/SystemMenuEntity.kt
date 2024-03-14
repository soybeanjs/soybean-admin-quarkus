package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_menu")
class SystemMenuEntity(

    /**
     * 菜单名称
     */
    @Column(name = "menu_name", nullable = false)
    var menuName: String? = null,

    /**
     * 菜单类型
     */
    @Column(name = "menu_type", nullable = false)
    var menuType: DbEnums.MenuItemType? = null,

    /**
     * 排序
     */
    @Column(name = "order", nullable = false)
    var order: Int? = null,

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id", nullable = false)
    var parentId: Long? = null,

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    var icon: String? = null,

    /**
     * 菜单图标类型
     */
    @Column(name = "icon_type")
    var iconType: String? = null,

    /**
     * 路由名称
     */
    @Column(name = "route_name")
    var routeName: String? = null,

    /**
     * 路由地址
     */
    @Column(name = "route_path")
    var routePath: String? = null,

    /**
     * 组件
     */
    @Column(name = "component")
    var component: String? = null,

    @Column(name = "i18n_key")
    var i18nKey: String? = null,

    @Column(name = "multi_tab")
    var multiTab: Boolean? = null,

    @Column(name = "active_menu")
    var activeMenu: String? = null,

    @Column(name = "hide_in_menu")
    var hideInMenu: Boolean? = null,

    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED
) : BaseEntity()
