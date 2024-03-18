package cn.soybean.system.application.eventhandler

import cn.soybean.system.domain.entity.SystemLoginLogEntity
import io.quarkus.logging.Log
import io.smallrye.mutiny.vertx.MutinyHelper
import io.vertx.core.Vertx
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.ObservesAsync
import org.hibernate.reactive.mutiny.Mutiny

@ApplicationScoped
class LoginLogEventHandler(private val sessionFactory: Mutiny.SessionFactory, private val vertx: Vertx) {

    fun handleLoginLogEvent(@ObservesAsync loginLogEntity: SystemLoginLogEntity) {
        sessionFactory.withStatelessSession { statelessSession ->
            statelessSession.insert(loginLogEntity)
        }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe().with(
                { Log.trace("LoginLog event processed successfully") },
                { throwable -> Log.error("Error processing LoginLog event: ${throwable.message}") }
            )
    }
}