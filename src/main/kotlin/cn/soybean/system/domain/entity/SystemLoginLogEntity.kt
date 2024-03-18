package cn.soybean.system.domain.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "sys_login_log")
class SystemLoginLogEntity(

    // 账号类别
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    val loginType: DbEnums.LoginType? = null,

    @Column(name = "action", length = DbConstants.LENGTH_20)
    val action: String? = null,

    @Column(name = "remark")
    val remark: String? = null,

    // 用户编号
    @Column(name = "user_id")
    val userId: Long? = null,

    @Column(name = "account_name", length = DbConstants.LENGTH_20)
    val accountName: String? = null,

    // IP
    @Column(name = "user_ip", columnDefinition = "INET")
    val userIp: String? = null,

    @Column(name = "user_port", nullable = true)
    var userPort: Int? = null,

    // UA
    @Column(name = "user_agent")
    val userAgent: String? = null
) : BaseTenantEntity()
