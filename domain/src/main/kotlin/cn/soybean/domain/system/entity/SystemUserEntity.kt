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
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_user",
    indexes = [
        Index(columnList = "tenant_id"),
        Index(columnList = "account_name"),
        Index(columnList = "email"),
        Index(columnList = "phone_number"),
    ],
)
open class SystemUserEntity(
    /**
     * 账号名称
     */
    @Column(name = "account_name", nullable = false, length = DbConstants.LENGTH_20)
    var accountName: String? = null,
    /**
     * 账号密码
     */
    @Column(name = "account_password")
    var accountPassword: String? = null,
    /**
     * 用户昵称
     */
    @Column(name = "nick_name", length = DbConstants.LENGTH_20)
    var nickName: String? = null,
    /**
     * 个人资料
     */
    @Column(name = "personal_profile")
    var personalProfile: String? = null,
    /**
     * 用户邮箱
     */
    @Column(name = "email", length = DbConstants.LENGTH_64)
    var email: String? = null,
    /**
     * 用户手机号码国家代码
     */
    @Column(name = "country_code", length = DbConstants.LENGTH_5)
    var countryCode: String = DbEnums.CountryInfo.CN.countryCode,
    /**
     * 用户手机号码国际区号
     */
    @Column(name = "phone_code", length = DbConstants.LENGTH_5)
    var phoneCode: String = DbEnums.CountryInfo.CN.phoneCode,
    /**
     * 用户手机号码
     */
    @Column(name = "phone_number", length = DbConstants.LENGTH_20)
    var phoneNumber: String? = null,
    /**
     * 用户性别
     */
    @Column(name = "gender")
    var gender: DbEnums.Gender? = null,
    /**
     * 用户头像
     */
    @Column(name = "avatar")
    var avatar: String? = null,
    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    var deptId: String? = null,
    /**
     * 是否内置
     */
    @Column(name = "built_in", nullable = false)
    val builtIn: Boolean = false,
    /**
     * 用户账号状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,
) : BaseTenantEntity()
