package cn.soybean.system.domain.entity

import cn.soybean.framework.common.consts.DbConstants
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "sys_apis")
class SystemApisEntity(

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
    @Column(name = "description", length = DbConstants.LENGTH_64)
    var description: String? = null,

    @Column(name = "create_time", updatable = false)
    var createTime: LocalDateTime? = null
) : PanacheEntityBase {
    companion object : PanacheCompanion<SystemApisEntity>;
}