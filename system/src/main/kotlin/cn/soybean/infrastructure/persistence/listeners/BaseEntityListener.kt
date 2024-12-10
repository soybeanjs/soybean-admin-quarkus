/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.infrastructure.persistence.listeners

import cn.soybean.domain.base.BaseEntity
import cn.soybean.infrastructure.security.LoginHelper
import io.quarkus.arc.Arc
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

// TODO 按ddd方法论domain应当自包含 但是一些注解 swagger又会侵入domain 难取舍 暂时废弃
@RegisterForReflection
class BaseEntityListener {
    private val loginHelper: LoginHelper by lazy {
        Arc.container().instance(LoginHelper::class.java).get()
    }

    @PrePersist
    fun setCreatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity ->
                obj.apply {
                    createBy = createBy ?: loginHelper.getUserId()
                    createAccountName = createAccountName.takeUnless { it.isNullOrBlank() } ?: loginHelper.getAccountName()
                    createTime = LocalDateTime.now()
                }
        }
    }

    @PreUpdate
    fun setUpdatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity ->
                obj.apply {
                    updateBy = updateBy ?: loginHelper.getUserId()
                    updateAccountName = updateAccountName.takeUnless { it.isNullOrBlank() } ?: loginHelper.getAccountName()
                    updateTime = LocalDateTime.now()
                }
        }
    }
}
