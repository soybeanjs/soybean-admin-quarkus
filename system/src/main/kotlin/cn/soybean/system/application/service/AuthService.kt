package cn.soybean.system.application.service

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.enums.DbEnums
import cn.soybean.domain.event.DomainEventPublisher
import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.infrastructure.security.LoginHelper.Companion.DEPT_KEY
import cn.soybean.infrastructure.security.LoginHelper.Companion.TENANT_KEY
import cn.soybean.infrastructure.security.LoginHelper.Companion.USER_AVATAR
import cn.soybean.infrastructure.security.LoginHelper.Companion.USER_KEY
import cn.soybean.interfaces.rest.util.Ip2RegionUtil
import cn.soybean.interfaces.rest.util.getClientIPAndPort
import cn.soybean.system.application.event.UserPermActionEvent
import cn.soybean.system.domain.entity.SystemLoginLogEntity
import cn.soybean.system.domain.entity.SystemTenantEntity
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.repository.SystemRoleRepository
import cn.soybean.system.domain.repository.SystemTenantRepository
import cn.soybean.system.domain.repository.SystemUserRepository
import cn.soybean.system.interfaces.rest.dto.request.PwdLoginRequest
import cn.soybean.system.interfaces.rest.vo.LoginRespVO
import com.github.yitter.idgen.YitIdHelper
import io.quarkus.elytron.security.common.BcryptUtil
import io.smallrye.jwt.build.Jwt
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Event
import org.eclipse.microprofile.jwt.Claims
import java.time.LocalDateTime

@ApplicationScoped
class AuthService(
    private val systemTenantRepository: SystemTenantRepository,
    private val systemUserRepository: SystemUserRepository,
    private val systemRoleRepository: SystemRoleRepository,
    private val routingContext: RoutingContext,
    private val eventBus: Event<SystemLoginLogEntity>,
    private val eventPublisher: DomainEventPublisher
) {

    fun pwdLogin(req: PwdLoginRequest): Uni<LoginRespVO> = findAndVerifyTenant(req.tenantName)
        .flatMap { tenant ->
            findAndVerifyUserCredentials(req.userName, req.password, tenant.id)
                .flatMap { user ->
                    systemRoleRepository.getRoleCodesByUserId(user.id)
                        .map { roles -> Triple(tenant, user, roles) }
                }
        }
        .map { (tenant, user, roles) ->
            createLoginRespVO(tenant, user, roles).also {
                saveLoginLog(user, tenant.id)
            }
        }

    fun findAndVerifyTenant(tenantName: String): Uni<SystemTenantEntity> = systemTenantRepository.findByName(tenantName)
        .onItem().ifNull().failWith(ServiceException(ErrorCode.TENANT_NOT_FOUND))
        .flatMap { tenant -> verifyTenantStatus(tenant) }

    fun verifyTenantStatus(tenant: SystemTenantEntity): Uni<SystemTenantEntity> = when {
        tenant.status == DbEnums.Status.DISABLED -> Uni.createFrom()
            .failure(ServiceException(ErrorCode.TENANT_DISABLED))

        LocalDateTime.now().isAfter(tenant.expireTime) ->
            Uni.createFrom().failure(ServiceException(ErrorCode.TENANT_EXPIRED))

        else -> Uni.createFrom().item(tenant)
    }

    fun findAndVerifyUserCredentials(username: String, password: String, tenantId: String): Uni<SystemUserEntity> =
        systemUserRepository.findByAccountNameOrEmailOrPhoneNumber(username, tenantId)
            .onItem().ifNull().failWith(ServiceException(ErrorCode.ACCOUNT_NOT_FOUND))
            .flatMap { user -> verifyUserCredentials(user, password) }

    fun verifyUserCredentials(user: SystemUserEntity, password: String): Uni<SystemUserEntity> = when {
        !BcryptUtil.matches(password, user.accountPassword) -> Uni.createFrom()
            .failure(ServiceException(ErrorCode.ACCOUNT_CREDENTIALS_INVALID))

        user.status == DbEnums.Status.DISABLED -> Uni.createFrom()
            .failure(ServiceException(ErrorCode.ACCOUNT_DISABLED))

        else -> Uni.createFrom().item(user)
    }

    private fun createLoginRespVO(
        tenantEntity: SystemTenantEntity,
        systemUserEntity: SystemUserEntity,
        roleCodes: Set<String>
    ): LoginRespVO {
        val tokenValue = Jwt.upn(systemUserEntity.accountName)
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
        return LoginRespVO(tokenValue, "")
    }

    private fun saveLoginLog(user: SystemUserEntity, tenantId: String) {
        val (ip, port) = getClientIPAndPort(routingContext.request())
        val loginLogEntity = SystemLoginLogEntity(
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