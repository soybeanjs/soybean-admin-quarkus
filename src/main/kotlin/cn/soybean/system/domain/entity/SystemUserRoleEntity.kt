package cn.soybean.system.domain.entity

import cn.soybean.domain.model.BaseTenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_user_role", indexes = [
        Index(columnList = "tenant_id"),
        Index(columnList = "user_id, role_id"),
        Index(columnList = "user_id"),
        Index(columnList = "role_id")
    ]
)
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