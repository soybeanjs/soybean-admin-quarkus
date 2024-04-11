package cn.soybean.system.domain.entity

import cn.soybean.domain.base.BaseTenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_role_user", indexes = [
        Index(columnList = "tenant_id"),
        Index(columnList = "user_id, role_id"),
        Index(columnList = "user_id"),
        Index(columnList = "role_id")
    ]
)
class SystemRoleUserEntity(

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    val userId: String? = null,

    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    val roleId: String? = null
) : BaseTenantEntity()