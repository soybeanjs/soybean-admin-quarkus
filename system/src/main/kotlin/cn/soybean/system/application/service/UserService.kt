package cn.soybean.system.application.service

import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.domain.entity.SystemUserEntity
import cn.soybean.system.domain.repository.SystemUserRepository
import io.quarkus.elytron.security.common.BcryptUtil
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserService(private val systemUserRepository: SystemUserRepository) {

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

    fun getUserList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemUserEntity> =
        systemUserRepository.getUserList(query, sort, params)
}