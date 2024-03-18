package cn.soybean.system.application.service

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.common.util.LoginHelper.Companion.DEPT_KEY
import cn.soybean.framework.common.util.LoginHelper.Companion.TENANT_KEY
import cn.soybean.framework.common.util.LoginHelper.Companion.USER_KEY
import cn.soybean.framework.common.util.getClientIPAndPort
import cn.soybean.framework.interfaces.exception.ErrorCode
import cn.soybean.framework.interfaces.exception.ServiceException
import cn.soybean.system.domain.entity.SystemLoginLogEntity
import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.interfaces.dto.PwdLoginDTO
import cn.soybean.system.interfaces.vo.LoginRespVO
import io.smallrye.jwt.build.Jwt
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import org.eclipse.microprofile.jwt.Claims

@ApplicationScoped
class AuthService(
    private val routingContext: RoutingContext,
    private val eventBus: Event<SystemLoginLogEntity>
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
}