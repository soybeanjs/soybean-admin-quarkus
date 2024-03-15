package cn.soybean.system.service

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.common.exception.ErrorCode
import cn.soybean.framework.common.exception.ServiceException
import cn.soybean.framework.common.utils.LoginHelper.Companion.DEPT_KEY
import cn.soybean.framework.common.utils.LoginHelper.Companion.TENANT_KEY
import cn.soybean.framework.common.utils.LoginHelper.Companion.USER_KEY
import cn.soybean.framework.common.utils.getClientIPAndPort
import cn.soybean.system.domain.entity.SystemLoginLogEntity
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.vo.LoginRespVO
import cn.soybean.system.dto.PwdLoginDTO
import io.quarkus.logging.Log
import io.smallrye.jwt.build.Jwt
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.vertx.MutinyHelper
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import jakarta.enterprise.event.ObservesAsync
import org.eclipse.microprofile.jwt.Claims
import org.hibernate.reactive.mutiny.Mutiny

@ApplicationScoped
class AuthService(
    private val routingContext: RoutingContext,
    private val eventBus: Event<SystemLoginLogEntity>,
    private val sessionFactory: Mutiny.SessionFactory,
    private val vertx: Vertx
) {

    fun pwdLogin(req: PwdLoginDTO): Uni<LoginRespVO> = SystemTenantEntity.findByName(req.tenantName)
        .onItem().ifNull().failWith(ServiceException(ErrorCode.TENANT_NOT_FOUND))
        .flatMap { tenant ->
            SystemTenantEntity.verifyTenantStatus(tenant)
                .flatMap {
                    SystemUserEntity.pwdAuthenticate(req.userName, req.password, tenant.id!!)
                        .map { user -> Pair(tenant, user) }
                }
        }
        .flatMap { pair ->
            SystemRoleEntity.getRoleCodeByUserId(pair.second.id!!)
                .map { roles -> Triple(pair.first, pair.second, roles) }
        }
        .map { triple ->
            createLoginRespVO(triple.first, triple.second, triple.third)
        }

    private fun createLoginRespVO(
        tenantEntity: SystemTenantEntity,
        systemUserEntity: SystemUserEntity,
        roleCodes: Set<String>
    ): LoginRespVO {
        val tokenValue = Jwt.upn(systemUserEntity.accountName)
            .subject(systemUserEntity.id.toString())
            .groups(roleCodes)
            .claim(TENANT_KEY, tenantEntity.id)
            .claim(USER_KEY, systemUserEntity.id)
            .claim(DEPT_KEY, systemUserEntity.deptId ?: "")
            .claim(Claims.nickname.name, systemUserEntity.nickName)
            .claim(Claims.gender.name, systemUserEntity.gender ?: "")
            .sign()
        saveLoginLog(systemUserEntity, tenantEntity.id)
        return LoginRespVO(tokenValue, "")
    }

    private fun saveLoginLog(user: SystemUserEntity, tenantId: Long?) {
        val (ip, port) = getClientIPAndPort(routingContext.request())
        val loginLogEntity = SystemLoginLogEntity(
            loginType = DbEnums.LoginType.PC,
            userId = user.id,
            accountName = user.accountName,
            userIp = ip,
            userPort = port,
            userAgent = routingContext.request().getHeader("User-Agent") ?: "Unknown",
        )
        loginLogEntity.tenantId = tenantId
        loginLogEntity.createBy = user.id
        loginLogEntity.createAccountName = user.accountName
        eventBus.fireAsync(loginLogEntity)
    }

    fun onLogEvent(@ObservesAsync loginLogEntity: SystemLoginLogEntity) {
        sessionFactory.withStatelessSession { statelessSession ->
            statelessSession.insert(loginLogEntity)
        }.runSubscriptionOn(MutinyHelper.executor(vertx.getOrCreateContext()))
            .subscribe().with(
                { Log.trace("LoginLog event processed successfully") },
                { throwable -> Log.error("Error processing LoginLog event: ${throwable.message}") }
            )
    }
}