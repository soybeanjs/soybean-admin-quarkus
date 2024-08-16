package cn.soybean.domain.system.entity

import cn.soybean.domain.isSuperUser
import cn.soybean.domain.system.config.DbConstants
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
        fun listApiByUserId(userId: String): Uni<List<SystemApisEntity>> = when {
            isSuperUser(userId) -> listAll()
            else -> list(
                """
                       SELECT a FROM SystemApisEntity a
                       LEFT JOIN SystemRoleApiEntity ra ON ra.operationId = a.operationId
                       LEFT JOIN SystemRoleUserEntity ru ON ru.roleId = ra.roleId
                       WHERE ru.userId = ?1
                """,
                userId
            )
        }
    }
}
