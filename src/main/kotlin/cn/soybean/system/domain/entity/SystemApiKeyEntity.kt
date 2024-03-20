package cn.soybean.system.domain.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_api_key")
class SystemApiKeyEntity(

    /**
     * key
     */
    @Column(name = "api_key", unique = true, nullable = false, length = DbConstants.LENGTH_64)
    var apiKey: String? = null,

    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    var type: DbEnums.ApiKeyType = DbEnums.ApiKeyType.SIMPLE,

    @Column(name = "used_count")
    var usedCount: Long = 0
) : BaseTenantEntity(), PanacheEntityBase {
    companion object : PanacheCompanion<SystemApiKeyEntity>;
}