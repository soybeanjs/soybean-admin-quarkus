package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_user_role")
class SystemUserRoleEntity(

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    val userId: Long? = null,

    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    val roleId: Long? = null
) : BaseTenantEntity()