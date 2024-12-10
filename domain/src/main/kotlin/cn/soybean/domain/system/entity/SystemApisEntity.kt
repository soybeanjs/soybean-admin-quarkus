package cn.soybean.domain.system.entity

import cn.soybean.domain.isSuperUser
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.isSuperRole
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import io.smallrye.mutiny.Uni
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "sys_apis")
open class SystemApisEntity(

    @Id
    @Column(name = "operation_id", length = DbConstants.LENGTH_64)
    var operationId: String? = null,

    /**
     * path
     */
    @Column(name = "path", length = DbConstants.LENGTH_64)
    var path: String? = null,

    /**
     * httpMethod
     */
    @Column(name = "method", length = DbConstants.LENGTH_20)
    var httpMethod: String? = null,

    /**
     * summary
     */
    @Column(name = "summary", length = DbConstants.LENGTH_64)
    var summary: String? = null,

    @Column(name = "permissions", length = DbConstants.LENGTH_64)
    var permissions: String? = null,

    var inclusive: Boolean? = null,

    /**
     * description
     */
    @Column(name = "description")
    var description: String? = null,

    @Column(name = "create_time", updatable = false)
    var createTime: LocalDateTime? = null
) : PanacheEntityBase {
    companion object : PanacheCompanion<SystemApisEntity> {
        fun listApi(userId: String, tenantId: String): Uni<List<SystemApisEntity>> = when {
            isSuperUser(userId) -> listAll().map { list -> list.filter { !it.permissions.isNullOrEmpty() } }

            else -> SystemTenantEntity.getTenantOperationIds(tenantId).flatMap { operationIds ->
                if (operationIds.isEmpty()) {
                    Uni.createFrom().item(emptyList())
                } else {
                    list(
                        "SELECT a FROM SystemApisEntity a WHERE a.operationId IN ?1 AND a.permissions IS NOT NULL",
                        operationIds
                    )
                }
            }
        }

        fun listApiOperationIdByRoleId(roleId: String, userId: String, tenantId: String): Uni<List<String>> = when {
            isSuperUser(userId) && isSuperRole(roleId) -> listAll().map { apis -> apis.mapNotNull { it.operationId } }

            else -> list(
                """
                       SELECT a FROM SystemApisEntity a
                       LEFT JOIN SystemRoleApiEntity ra ON ra.operationId = a.operationId
                       WHERE ra.roleId = ?1 AND ra.tenantId = ?2
                """, roleId, tenantId
            ).map { apis -> apis.mapNotNull { it.operationId } }
        }
    }
}
