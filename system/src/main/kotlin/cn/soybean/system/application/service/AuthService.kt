/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.service

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.event.DomainEventPublisher
import cn.soybean.domain.system.entity.SystemLoginLogEntity
import cn.soybean.domain.system.entity.SystemTenantEntity
import cn.soybean.domain.system.entity.SystemUserEntity
import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper.Companion.DEPT_KEY
import cn.soybean.infrastructure.security.LoginHelper.Companion.TENANT_KEY
import cn.soybean.infrastructure.security.LoginHelper.Companion.USER_AVATAR
import cn.soybean.infrastructure.security.LoginHelper.Companion.USER_KEY
import cn.soybean.interfaces.rest.util.Ip2RegionUtil
import cn.soybean.interfaces.rest.util.getClientIPAndPort
import cn.soybean.system.application.command.auth.PwdLoginCommand
import cn.soybean.system.application.event.UserPermActionEvent
import cn.soybean.system.application.query.role.RoleCodeByUserIdQuery
import cn.soybean.system.application.query.role.service.RoleQueryService
import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import cn.soybean.system.application.query.user.UserByAccountQuery
import cn.soybean.system.application.query.user.service.UserQueryService
import cn.soybean.system.interfaces.rest.dto.response.auth.LoginResponse
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.elytron.security.common.BcryptUtil
import io.smallrye.jwt.build.Jwt
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import java.time.LocalDateTime
import org.eclipse.microprofile.jwt.Claims

@ApplicationScoped
class AuthService(
    private val tenantQueryService: TenantQueryService,
    private val userQueryService: UserQueryService,
    private val roleQueryService: RoleQueryService,
    private val routingContext: RoutingContext,
    private val eventBus: Event<SystemLoginLogEntity>,
    private val eventPublisher: DomainEventPublisher,
) {
    fun pwdLogin(command: PwdLoginCommand): Uni<LoginResponse> = findAndVerifyTenant(command.tenantName)
        .flatMap { tenant ->
            findAndVerifyUserCredentials(command.userName, command.password, tenant.id)
                .flatMap { user ->
                    roleQueryService.handle(RoleCodeByUserIdQuery(user.id))
                        .map { roles -> Triple(tenant, user, roles) }
                }
        }
        .map { (tenant, user, roles) ->
            createLoginResponse(tenant, user, roles).also {
                saveLoginLog(user, tenant.id)
            }
        }

    fun findAndVerifyTenant(tenantName: String): Uni<SystemTenantEntity> = tenantQueryService.handle(TenantByNameQuery(tenantName))
        .onItem().ifNull().failWith(ServiceException(ErrorCode.TENANT_NOT_FOUND))
        .flatMap { tenant -> verifyTenantStatus(tenant) }

    fun verifyTenantStatus(tenant: SystemTenantEntity): Uni<SystemTenantEntity> = when {
        tenant.status == DbEnums.Status.DISABLED ->
            Uni.createFrom()
                .failure(ServiceException(ErrorCode.TENANT_DISABLED))

        LocalDateTime.now().isAfter(tenant.expireTime) ->
            Uni.createFrom().failure(ServiceException(ErrorCode.TENANT_EXPIRED))

        else -> Uni.createFrom().item(tenant)
    }

    fun findAndVerifyUserCredentials(accountName: String, password: String, tenantId: String): Uni<SystemUserEntity> =
        userQueryService.handle(UserByAccountQuery(accountName, tenantId))
            .onItem().ifNull().failWith(ServiceException(ErrorCode.ACCOUNT_NOT_FOUND))
            .flatMap { user -> verifyUserCredentials(user, password) }

    fun verifyUserCredentials(user: SystemUserEntity, password: String): Uni<SystemUserEntity> = when {
        !BcryptUtil.matches(password, user.accountPassword) ->
            Uni.createFrom()
                .failure(ServiceException(ErrorCode.ACCOUNT_CREDENTIALS_INVALID))

        user.status == DbEnums.Status.DISABLED ->
            Uni.createFrom()
                .failure(ServiceException(ErrorCode.ACCOUNT_DISABLED))

        else -> Uni.createFrom().item(user)
    }

    private fun createLoginResponse(
        tenantEntity: SystemTenantEntity,
        systemUserEntity: SystemUserEntity,
        roleCodes: Set<String>,
    ): LoginResponse {
        val tokenValue =
            Jwt.upn(systemUserEntity.accountName)
                .subject(systemUserEntity.id)
                .groups(roleCodes + AppConstants.APP_COMMON_ROLE)
                .claim(TENANT_KEY, tenantEntity.id)
                .claim(USER_KEY, systemUserEntity.id)
                .claim(DEPT_KEY, systemUserEntity.deptId ?: "")
                .claim(USER_AVATAR, systemUserEntity.avatar ?: "")
                .claim(Claims.nickname.name, systemUserEntity.nickName)
                .claim(Claims.gender.name, systemUserEntity.gender ?: "")
                .sign()
        UserPermActionEvent(systemUserEntity.id).also { eventPublisher.publish(it) }
        return LoginResponse(tokenValue, "")
    }

    private fun saveLoginLog(user: SystemUserEntity, tenantId: String) {
        val (ip, port) = getClientIPAndPort(routingContext.request())
        val loginLogEntity =
            SystemLoginLogEntity(
                loginType = DbEnums.LoginType.PC,
                userId = user.id,
                accountName = user.accountName,
                loginIp = ip,
                loginRegion = Ip2RegionUtil.search(ip),
                loginPort = port,
                userAgent = routingContext.request().getHeader("User-Agent") ?: "Unknown",
            )
        loginLogEntity.id = YitIdHelper.nextId().toString()
        loginLogEntity.tenantId = tenantId
        loginLogEntity.createBy = user.id
        loginLogEntity.createAccountName = user.accountName
        eventBus.fireAsync(loginLogEntity)
    }
}
