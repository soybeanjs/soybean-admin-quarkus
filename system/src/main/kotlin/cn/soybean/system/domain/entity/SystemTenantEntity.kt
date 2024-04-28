package cn.soybean.system.domain.entity

import cn.soybean.domain.base.BaseEntity
import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.infrastructure.persistence.converters.JsonStringSetTypeHandler
import cn.soybean.system.domain.config.DbConstants
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.mcarle.konvert.api.KonvertTo
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "sys_tenant", indexes = [
        Index(columnList = "name"),
        Index(columnList = "contact_user_id")
    ]
)
@KonvertTo(TenantResponse::class)
class SystemTenantEntity(

    /**
     * 租户名称
     */
    @Column(name = "name", unique = true, nullable = false, length = DbConstants.LENGTH_20)
    var name: String? = null,

    /**
     * 租户联系人账号ID
     */
    @Column(name = "contact_user_id", nullable = false)
    val contactUserId: String? = null,

    /**
     * 租户联系人账号名称
     */
    @Column(name = "contact_account_name", nullable = false, length = DbConstants.LENGTH_20)
    var contactAccountName: String? = null,

    /**
     * 租户状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     * 租户域名网站
     */
    @Column(name = "website", length = DbConstants.LENGTH_64)
    var website: String? = null,

    /**
     * 是否内置
     */
    @Column(name = "builtin", nullable = false)
    val builtin: Boolean = false,

    /**
     * 租户过期时间
     */
    @Column(name = "expire_time", nullable = false)
    var expireTime: LocalDateTime? = null,

    @Column(name = "menu_ids")
    @Convert(converter = JsonStringSetTypeHandler::class)
    var menuIds: Set<String>? = null,

    @Column(name = "operation_ids")
    @Convert(converter = JsonStringSetTypeHandler::class)
    var operationIds: Set<String>? = null
) : BaseEntity()