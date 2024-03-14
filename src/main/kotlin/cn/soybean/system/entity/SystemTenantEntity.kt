package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "sys_tenant")
class SystemTenantEntity(

    /**
     * 租户名称
     */
    @Column(name = "name", nullable = false)
    var name: String? = null,

    /**
     * 租户联系人账号ID
     */
    @Column(name = "contact_user_id", nullable = false)
    val contactUserId: Long? = null,

    /**
     * 租户联系人账号名称
     */
    @Column(name = "contact_account_name", nullable = false)
    var contactAccountName: String? = null,

    /**
     * 租户状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     * 租户域名网站
     */
    @Column(name = "website")
    var website: String? = null,

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