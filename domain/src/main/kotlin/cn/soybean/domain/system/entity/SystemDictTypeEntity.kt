/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseEntity
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_dict_type",
    indexes = [
        Index(columnList = "dict_type"),
    ],
)
open class SystemDictTypeEntity(
    /**
     * 字典名称
     */
    @Column(name = "dict_name", nullable = false, length = DbConstants.LENGTH_20)
    var dictName: String? = null,
    /**
     * 字典类型
     */
    @Column(name = "dict_type", unique = true, nullable = false, length = DbConstants.LENGTH_20)
    var dictType: String? = null,
    /**
     * 状态
     */
    var status: DbEnums.Status = DbEnums.Status.ENABLED,
    /**
     * 备注
     */
    var remark: String? = null,
) : BaseEntity()
