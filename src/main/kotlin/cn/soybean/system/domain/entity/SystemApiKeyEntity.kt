package cn.soybean.system.domain.entity

import cn.soybean.domain.enums.DbEnums
import cn.soybean.domain.model.BaseTenantEntity
import cn.soybean.infrastructure.config.consts.DbConstants
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_api_key")
class SystemApiKeyEntity : BaseTenantEntity(), PanacheEntityBase {

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