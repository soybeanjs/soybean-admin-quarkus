/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.base

import cn.soybean.domain.system.config.DbConstants
import cn.soybean.shared.domain.entity.EntityBase
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.io.Serializable
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@MappedSuperclass
abstract class BaseEntity : EntityBase<String>(), Serializable {
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    open lateinit var createTime: LocalDateTime

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time", insertable = false)
    open var updateTime: LocalDateTime? = null

    /**
     * 创建人
     */
    @Column(name = "create_by", updatable = false)
    open var createBy: String? = null

    /**
     * 创建人名称
     */
    @Column(name = "create_account_name", updatable = false, length = DbConstants.LENGTH_20)
    open var createAccountName: String? = null

    /**
     * 更新人
     */
    @Column(name = "update_by", insertable = false)
    open var updateBy: String? = null

    /**
     * 更新人名称
     */
    @Column(name = "update_account_name", insertable = false, length = DbConstants.LENGTH_20)
    open var updateAccountName: String? = null
}
