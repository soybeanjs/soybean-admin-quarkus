package cn.soybean.system.application.bootstrap

import cn.soybean.system.domain.entity.SystemApiKeyEntity
import cn.soybean.system.domain.entity.SystemApisEntity
import cn.soybean.system.infrastructure.security.ApiKeyCache
import cn.soybean.system.infrastructure.web.ApiEndpointDynamicFeature.Companion.apiEndpoints
import cn.soybean.system.infrastructure.web.toSystemApisEntity
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.runtime.StartupEvent
import io.quarkus.vertx.VertxContextSupport
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import jakarta.enterprise.event.Observes
import java.time.LocalDateTime

@ApplicationScoped
class SystemBootStrapRecorder(
    private val eventBus: Event<Set<SystemApisEntity>>,
    private val apiKeyCache: ApiKeyCache
) {

    fun onStart(@Observes ev: StartupEvent) {
        initApiEndpoints()

        initApiKeyCache()
    }

    private fun initApiKeyCache() {
        VertxContextSupport.subscribeAndAwait {
            Panache.withSession {
                SystemApiKeyEntity.listAll().map { apiKey ->
                    apiKey.forEach {
                        apiKeyCache.set(it.apiKey, it)
                    }
                }
            }
        }
    }

    private fun initApiEndpoints() {
        val now = LocalDateTime.now()
        eventBus.fireAsync(apiEndpoints
            .map { it.toSystemApisEntity() }
            .map {
                it.createTime = now
                it
            }.toSet()
        )
    }
}