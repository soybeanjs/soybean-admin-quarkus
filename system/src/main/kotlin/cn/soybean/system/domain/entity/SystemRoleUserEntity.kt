package cn.soybean.system.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(
    name = "sys_role_user", indexes = [
        Index(columnList = "tenant_id, role_id, user_id"),
    ]
)
@IdClass(SystemRoleUser::class)
class SystemRoleUserEntity(

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", nullable = false)
    val roleId: String? = null,

    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id", nullable = false)
    val userId: String? = null,

    @Id
    @Column(name = "tenant_id", nullable = false)
    val tenantId: String? = null
)

data class SystemRoleUser(
    val roleId: String? = null,
    val userId: String? = null,
    val tenantId: String? = null
) : Serializable
