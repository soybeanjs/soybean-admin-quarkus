package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_role_menu")
class SystemRoleMenuEntity(

    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    val roleId: Long? = null,

    /**
     * 菜单ID
     */
    @Column(name = "menu_id", nullable = false)
    val menuId: Long? = null
) : BaseTenantEntity()
