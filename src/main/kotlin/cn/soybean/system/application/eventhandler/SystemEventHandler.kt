package cn.soybean.system.application.eventhandler

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.domain.entity.SystemApisEntity
import cn.soybean.system.domain.entity.SystemLoginLogEntity
import cn.soybean.system.infrastructure.dto.UserPermAction
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.logging.Log
import io.quarkus.vertx.VertxContextSupport
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.vertx.MutinyHelper
import io.vertx.core.Vertx
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.ObservesAsync
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hibernate.reactive.mutiny.Mutiny
import org.redisson.api.RedissonClient
import java.time.Duration
import java.util.*

@ApplicationScoped
class SystemEventHandler(
    private val sessionFactory: Mutiny.SessionFactory,
    private val vertx: Vertx,
    private val redissonClient: RedissonClient
) {

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.token.age")
    private lateinit var mpJwtVerifyTokenAge: Optional<Long>

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

    fun handleUserPermActionEvent(@ObservesAsync userPermAction: UserPermAction) {
        sessionFactory.withStatelessSession { statelessSession ->
            when {
                isSuperUser(userPermAction.userId) -> getApiPermAction(statelessSession).map { apis ->
                    storeUserPermAction(apis, userPermAction)
                }

                else -> TODO()
            }
        }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe().with(
                { Log.trace("UserPermAction event processed successfully. userId: ${userPermAction.userId}") },
                { throwable -> Log.error("Error processing UserPermAction event: ${throwable.message}") }
            )
    }

    private fun storeUserPermAction(apis: List<SystemApisEntity>, userPermAction: UserPermAction) {
        val permAction = apis.asSequence()
            .mapNotNull { it.permissions }
            .flatMap { it.split(",").asSequence().map(String::trim) }
            .filterNot { it.isBlank() }
            .toSet()
        val permissionsKey = "${AppConstants.APP_PERM_ACTION_CACHE_PREFIX}:${userPermAction.userId}"
        val permissions = redissonClient.getSet<String>(permissionsKey)

        permissions.deleteAsync()
            .thenComposeAsync { permissions.addAllAsync(permAction) }
            .thenAcceptAsync { permissions.expire(Duration.ofSeconds(mpJwtVerifyTokenAge.get())) }
    }

    private fun getApiPermAction(statelessSession: Mutiny.StatelessSession): Uni<List<SystemApisEntity>> =
        statelessSession.createQuery(
            "FROM SystemApisEntity",
            SystemApisEntity::class.java
        ).resultList
}