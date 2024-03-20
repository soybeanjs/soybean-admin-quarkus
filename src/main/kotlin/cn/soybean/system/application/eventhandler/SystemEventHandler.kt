package cn.soybean.system.application.eventhandler

import cn.soybean.system.domain.entity.SystemApisEntity
import cn.soybean.system.domain.entity.SystemLoginLogEntity
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.logging.Log
import io.quarkus.vertx.VertxContextSupport
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.vertx.MutinyHelper
import io.vertx.core.Vertx
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.ObservesAsync
import org.hibernate.reactive.mutiny.Mutiny

@ApplicationScoped
class SystemEventHandler(private val sessionFactory: Mutiny.SessionFactory, private val vertx: Vertx) {

    fun handleLoginLogEvent(@ObservesAsync loginLogEntity: SystemLoginLogEntity) {
        sessionFactory.withStatelessSession { statelessSession ->
            statelessSession.insert(loginLogEntity)
        }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe().with(
                { Log.trace("LoginLog event processed successfully") },
                { throwable -> Log.error("Error processing LoginLog event: ${throwable.message}") }
            )
    }

    fun handleSystemApisEvent(@ObservesAsync apisEntities: Set<SystemApisEntity>) {
        VertxContextSupport.subscribeWith(
            { Multi.createFrom().item(apisEntities) },
            { item ->
                Panache.withTransaction {
                    SystemApisEntity.deleteAll()
                        .flatMap { SystemApisEntity.persist(item) }
                }.subscribe().with(
                    { Log.trace("ApisEntity event processed successfully") },
                    { throwable -> Log.error("Error processing ApisEntity event: ${throwable.message}") }
                )
            }
        )
    }
}