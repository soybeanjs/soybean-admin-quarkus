package cn.soybean.system.domain.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "sys_tenant", indexes = [
        Index(columnList = "name"),
        Index(columnList = "contact_user_id"),
        Index(columnList = "website"),
        Index(columnList = "package_id")
    ]
)
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
    val contactUserId: Long? = null,

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
     * 租户套餐ID
     */
    @Column(name = "package_id", nullable = false)
    var packageId: Long? = null,

    /**
     * 租户过期时间
     */
    @Column(name = "expire_time", nullable = false)
    var expireTime: LocalDateTime? = null
) : BaseEntity()