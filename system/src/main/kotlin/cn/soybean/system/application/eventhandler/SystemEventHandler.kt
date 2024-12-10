/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.eventhandler

import cn.soybean.domain.system.entity.SystemApisEntity
import cn.soybean.domain.system.entity.SystemLoginLogEntity
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.interceptor.dto.OperationLogDTO
import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.application.event.ApiEndpointEvent
import cn.soybean.system.application.event.UserPermActionEvent
import cn.soybean.system.infrastructure.web.toSystemApisEntity
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.logging.Log
import io.quarkus.redis.client.RedisClientName
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.quarkus.redis.datasource.set.ReactiveSetCommands
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
import java.time.Duration
import java.util.Optional

@ApplicationScoped
class SystemEventHandler(
    private val sessionFactory: Mutiny.SessionFactory,
    private val vertx: Vertx,
    @RedisClientName("sign-redis") private val reactiveRedisDataSource: ReactiveRedisDataSource,
) {
    @Inject
    @ConfigProperty(name = "mp.jwt.verify.token.age")
    private lateinit var mpJwtVerifyTokenAge: Optional<Long>

    fun handleLoginLogEvent(
        @ObservesAsync loginLogEntity: SystemLoginLogEntity,
    ) {
        sessionFactory
            .withStatelessSession { statelessSession ->
                statelessSession.insert(loginLogEntity)
            }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe()
            .with(
                { Log.trace("[SystemEventHandler] LoginLog event processed successfully") },
                { ex -> Log.errorf(ex, "[SystemEventHandler] Error processing LoginLog event") },
            )
    }

    fun handleSystemApisEvent(
        @ObservesAsync apiEndpointEvent: ApiEndpointEvent,
    ) {
        VertxContextSupport.subscribeWith(
            { Multi.createFrom().item(apiEndpointEvent.data) },
            { item ->
                Panache
                    .withTransaction {
                        SystemApisEntity
                            .deleteAll()
                            .flatMap { SystemApisEntity.persist(item.map { it.toSystemApisEntity() }.toList()) }
                    }.subscribe()
                    .with(
                        { Log.trace("[SystemEventHandler] ApisEntity event processed successfully") },
                        { ex -> Log.errorf(ex, "[SystemEventHandler] Error processing ApisEntity event") },
                    )
            },
        )
    }

    fun handleUserPermActionEvent(
        @ObservesAsync userPermActionEvent: UserPermActionEvent,
    ) {
        sessionFactory
            .withStatelessSession { statelessSession ->
                when {
                    isSuperUser(userPermActionEvent.userId) ->
                        getApiPermAction(statelessSession).map { apis ->
                            storeUserPermAction(apis, userPermActionEvent.userId)
                        }

                    else ->
                        getApiPermActionByUserId(statelessSession, userPermActionEvent.userId).map { apis ->
                            storeUserPermAction(apis, userPermActionEvent.userId)
                        }
                }
            }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe()
            .with(
                {
                    Log.trace(
                        "[SystemEventHandler] UserPermActionEvent event processed successfully. userId: ${userPermActionEvent.userId}",
                    )
                },
                { ex -> Log.errorf(ex, "[SystemEventHandler] Error processing UserPermActionEvent event") },
            )
    }

    fun handleOperationLogEvent(
        @ObservesAsync operationLog: OperationLogDTO,
    ) {
        sessionFactory
            .withStatelessSession { statelessSession ->
                statelessSession.insert(operationLog.toOperateLogEntity())
            }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe()
            .with(
                { Log.trace("[SystemEventHandler] OperationLogDTO event processed successfully") },
                { ex -> Log.errorf(ex, "[SystemEventHandler] Error processing OperationLogDTO event") },
            )
    }

    fun storeUserPermAction(
        apis: List<SystemApisEntity>,
        userId: String,
    ) {
        val permissionsKey = "${AppConstants.APP_PERM_ACTION_CACHE_PREFIX}:$userId"
        val permAction = extractPermissions(apis)

        when {
            permAction.isEmpty() -> return
            else -> {
                val commands: ReactiveSetCommands<String, String> =
                    reactiveRedisDataSource.set(String::class.java, String::class.java)

                reactiveRedisDataSource
                    .key()
                    .del(permissionsKey)
                    .flatMap { commands.sadd(permissionsKey, *permAction.toTypedArray()) }
                    .flatMap {
                        reactiveRedisDataSource
                            .key()
                            .expire(permissionsKey, Duration.ofSeconds(mpJwtVerifyTokenAge.get()))
                    }.subscribe()
                    .with { _ ->
                        Log.info("[SystemEventHandler] storeUserPermAction processed successfully, Permissions updated for userId $userId")
                    }
            }
        }
    }

    private fun extractPermissions(apis: List<SystemApisEntity>): Set<String> =
        apis
            .asSequence()
            .mapNotNull { it.permissions }
            .flatMap { it.split(",").asSequence().map(String::trim) }
            .filterNot { it.isBlank() }
            .toSet()

    private fun getApiPermAction(statelessSession: Mutiny.StatelessSession): Uni<List<SystemApisEntity>> =
        statelessSession
            .createQuery(
                "FROM SystemApisEntity",
                SystemApisEntity::class.java,
            ).resultList

    private fun getApiPermActionByUserId(
        statelessSession: Mutiny.StatelessSession,
        userId: String,
    ): Uni<List<SystemApisEntity>> =
        statelessSession
            .createQuery(
                """
                SELECT a
                FROM SystemApisEntity a
                     LEFT JOIN SystemRoleApiEntity ra ON ra.operationId = a.operationId
                     LEFT JOIN SystemRoleUserEntity ru ON ru.roleId = ra.roleId
                WHERE ru.userId = :userId
            """,
                SystemApisEntity::class.java,
            ).setParameter("userId", userId)
            .resultList
}
