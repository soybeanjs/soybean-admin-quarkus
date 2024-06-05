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
    name = "sys_role_menu", indexes = [
        Index(columnList = "tenant_id, role_id, menu_id"),
    ]
)
@IdClass(SystemRoleMenu::class)
class SystemRoleMenuEntity(

    @Id
    @Column(name = "role_id", nullable = false)
    val roleId: String? = null,

    @Id
    @Column(name = "menu_id", nullable = false)
    val menuId: String? = null,

    @Id
    @Column(name = "tenant_id", nullable = false)
    val tenantId: String? = null
)

data class SystemRoleMenu(
    val roleId: String? = null,
    val menuId: String? = null,
    val tenantId: String? = null
) : Serializable
