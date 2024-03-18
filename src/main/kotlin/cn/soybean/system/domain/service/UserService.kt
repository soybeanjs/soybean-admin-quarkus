package cn.soybean.system.domain.service

import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.interfaces.exception.ErrorCode
import cn.soybean.framework.interfaces.exception.ServiceException
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.repository.SystemUserRepository
import io.quarkus.elytron.security.common.BcryptUtil
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserService(private val systemUserRepository: SystemUserRepository) {

    fun findAndVerifyUserCredentials(username: String, password: String, tenantId: Long): Uni<SystemUserEntity> =
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
}