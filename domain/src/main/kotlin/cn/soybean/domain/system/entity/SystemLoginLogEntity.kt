/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "sys_login_log")
open class SystemLoginLogEntity(
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
    val userId: String? = null,
    @Column(name = "account_name", length = DbConstants.LENGTH_20)
    val accountName: String? = null,
    // IP
    @Column(name = "login_ip", length = DbConstants.LENGTH_20)
    val loginIp: String? = null,
    @Column(name = "login_region", length = DbConstants.LENGTH_64)
    val loginRegion: String? = null,
    @Column(name = "login_port", nullable = true)
    var loginPort: Int? = null,
    // UA
    @Column(name = "user_agent")
    val userAgent: String? = null,
) : BaseTenantEntity()
