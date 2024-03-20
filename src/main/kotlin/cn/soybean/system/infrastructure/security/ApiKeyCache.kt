package cn.soybean.system.infrastructure.security

import cn.soybean.system.domain.entity.SystemApiKeyEntity
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