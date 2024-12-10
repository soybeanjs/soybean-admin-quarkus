/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.infrastructure.persistence.listeners

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.infrastructure.security.LoginHelper
import io.quarkus.arc.Arc
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.PrePersist

@RegisterForReflection
class BaseTenantEntityListener {
    private val loginHelper: LoginHelper by lazy {
        Arc.container().instance(LoginHelper::class.java).get()
    }

    @PrePersist
    fun setTenantId(obj: Any) {
        when (obj) {
            is BaseTenantEntity -> {
                obj.tenantId = obj.tenantId ?: loginHelper.getTenantId()
            }
        }
    }
}
