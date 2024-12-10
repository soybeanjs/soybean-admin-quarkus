/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.domain.system.config.DbConstants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_dept",
    indexes = [
        Index(columnList = "tenant_id"),
    ],
)
open class SystemDeptEntity(
    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false, length = DbConstants.LENGTH_20)
    var name: String? = null,
    /**
     * 父部门ID
     */
    @Column(name = "parent_id")
    var parentId: String? = null,
    /**
     * 排序
     */
    @Column(name = "sequence")
    var order: Int? = null,
    /**
     * 部门领导ID
     */
    @Column(name = "leader_user_id")
    var leaderUserId: String? = null,
    /**
     * 部门领导账号名称
     */
    @Column(name = "leader_account_name", length = DbConstants.LENGTH_20)
    var leaderAccountName: String? = null,
    @Column(name = "remark")
    var remark: String? = null,
) : BaseTenantEntity()
