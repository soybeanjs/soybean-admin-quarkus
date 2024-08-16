package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_api_key")
open class SystemApiKeyEntity : BaseTenantEntity(), PanacheEntityBase {

    /**
     * key
     */
    @Column(name = "api_key", unique = true, nullable = false, length = DbConstants.LENGTH_64)
    lateinit var apiKey: String

    @Column(name = "api_secret", unique = true, nullable = false, length = DbConstants.LENGTH_64)
    lateinit var apiSecret: String

    var status: DbEnums.Status = DbEnums.Status.ENABLED

    var type: DbEnums.ApiKeyType = DbEnums.ApiKeyType.SIMPLE

    @Column(name = "used_count")
    var usedCount: Long = 0

    companion object : PanacheCompanion<SystemApiKeyEntity>
}
