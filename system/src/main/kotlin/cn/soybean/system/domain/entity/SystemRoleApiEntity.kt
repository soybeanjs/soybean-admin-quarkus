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
    name = "sys_role_api", indexes = [
        Index(columnList = "tenant_id, role_id, operation_id"),
    ]
)
@IdClass(SystemRoleApi::class)
class SystemRoleApiEntity(

    @Id
    @Column(name = "role_id", nullable = false)
    val roleId: String? = null,

    @Id
    @Column(name = "operation_id", nullable = false)
    val operationId: String? = null,

    @Id
    @Column(name = "tenant_id", nullable = false)
    val tenantId: String? = null
)

data class SystemRoleApi(
    val roleId: String? = null,
    val operationId: String? = null,
    val tenantId: String? = null
) : Serializable
