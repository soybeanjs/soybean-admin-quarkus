/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseTenantEntity : BaseEntity() {
    /**
     * 租户ID
     *
     * SCHEMA租户模式下不需要定义此字段和注解
     * DISCRIMINATOR租户模式下启用
     */
    @Column(name = "tenant_id", nullable = false)
    open var tenantId: String? = null
}
