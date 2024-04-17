package cn.soybean.system.application.bootstrap

import cn.soybean.domain.event.DomainEventPublisher
import cn.soybean.interfaces.rest.util.Ip2RegionUtil
import cn.soybean.system.application.event.ApiEndpointEvent
import cn.soybean.system.domain.entity.SystemApiKeyEntity
import cn.soybean.system.infrastructure.security.ApiKeyCache
import cn.soybean.system.infrastructure.web.ApiEndpointDynamicFeature.Companion.apiEndpoints
import com.github.yitter.contract.IdGeneratorOptions
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.runtime.StartupEvent
import io.quarkus.vertx.VertxContextSupport
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import java.time.LocalDateTime

@ApplicationScoped
class SystemBootStrapRecorder(
    private val eventPublisher: DomainEventPublisher,
    private val apiKeyCache: ApiKeyCache
) {

    fun onStart(@Observes ev: StartupEvent) {
        initApiEndpoints()

        initApiKeyCache()

        initYitterIdGenerator()

        Ip2RegionUtil.initialize()
    }

    private fun initYitterIdGenerator() {
        val options = IdGeneratorOptions()
        YitIdHelper.setIdGenerator(options)
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
        apiEndpoints.forEach { it.createTime = now }
        eventPublisher.publish(ApiEndpointEvent(apiEndpoints))
    }
}