package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseEntity
import cn.soybean.domain.isSuperUser
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.isSuperRole
import cn.soybean.shared.infrastructure.persistence.converters.JsonStringListConverter
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import io.smallrye.mutiny.Uni
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_menu")
open class SystemMenuEntity(

    /**
     * 菜单名称
     */
    @Column(name = "menu_name", nullable = false, length = DbConstants.LENGTH_20)
    var menuName: String? = null,

    /**
     * 菜单类型
     */
    @Column(name = "menu_type", nullable = false)
    var menuType: DbEnums.MenuItemType? = null,

    /**
     * 排序
     */
    @Column(name = "sequence", nullable = false)
    var order: Int? = null,

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id", nullable = false)
    var parentId: String? = null,

    /**
     * 菜单图标
     */
    @Column(name = "icon", length = DbConstants.LENGTH_64)
    var icon: String? = null,

    /**
     * 菜单图标类型
     */
    @Column(name = "icon_type", length = DbConstants.LENGTH_20)
    var iconType: String? = null,

    /**
     * 路由名称
     */
    @Column(name = "route_name", unique = true, nullable = false, length = DbConstants.LENGTH_64)
    var routeName: String? = null,

    /**
     * 路由地址
     */
    @Column(name = "route_path", length = DbConstants.LENGTH_128)
    var routePath: String? = null,

    /**
     * 组件
     */
    @Column(name = "component", length = DbConstants.LENGTH_64)
    var component: String? = null,

    @Column(name = "i18n_key", length = DbConstants.LENGTH_64)
    var i18nKey: String? = null,

    @Column(name = "multi_tab")
    var multiTab: Boolean = false,

    @Column(name = "active_menu", length = DbConstants.LENGTH_64)
    var activeMenu: String? = null,

    @Column(name = "hide_in_menu")
    var hideInMenu: Boolean = false,

    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    @Convert(converter = JsonStringListConverter::class)
    var roles: List<String>? = null,

    @Column(name = "keep_alive")
    var keepAlive: Boolean = false,

    @Column(name = "constant")
    var constant: Boolean = false,

    @Column(name = "href", length = DbConstants.LENGTH_64)
    var href: String? = null,

    /**
     * 是否内置
     */
    @Column(name = "built_in", nullable = false)
    val builtIn: Boolean = false
) : BaseEntity(), PanacheEntityBase {
    companion object : PanacheCompanion<SystemMenuEntity> {
        fun listMenuIdByRoleId(roleId: String, userId: String, tenantId: String): Uni<List<String>> = when {
            isSuperUser(userId) && isSuperRole(roleId) -> listAll().map { menus -> menus.map { it.id } }

            else -> list(
                """
                       SELECT m FROM SystemMenuEntity m
                       LEFT JOIN SystemRoleMenuEntity rm ON rm.menuId = m.id
                       WHERE rm.roleId = ?1 AND rm.tenantId = ?2
                """, roleId, tenantId
            ).map { menus -> menus.map { it.id } }
        }
    }
}
