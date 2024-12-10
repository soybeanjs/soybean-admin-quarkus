/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.infrastructure.security

import cn.soybean.domain.system.entity.SystemApiKeyEntity
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ApiKeyCache {
    private val cache: Cache<String, SystemApiKeyEntity> = Caffeine.newBuilder().build()

    fun set(key: String, value: SystemApiKeyEntity) = cache.put(key, value)

    fun get(key: String): SystemApiKeyEntity? = cache.getIfPresent(key)

    fun delete(key: String) = cache.invalidate(key)
}
