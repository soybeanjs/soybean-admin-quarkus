package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.DeleteUserCommand
import cn.soybean.system.application.command.UpdateUserCommand
import cn.soybean.system.application.query.UserByEmail
import cn.soybean.system.application.query.UserById
import cn.soybean.system.application.query.UserByPhoneNumber
import cn.soybean.system.application.query.UserByaAccountName
import cn.soybean.system.application.query.service.UserQueryService
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserService(private val userQueryService: UserQueryService, private val commandInvoker: CommandInvoker) {

    fun createUser(command: CreateUserCommand, tenantId: String): Uni<Boolean> =
        validateUserForCreateOrUpdate(null, tenantId, command.accountName, command.email, command.phoneNumber).flatMap {
            commandInvoker.dispatch<CreateUserCommand, Boolean>(command).map { it }
        }

    fun updateUser(command: UpdateUserCommand, tenantId: String): Uni<Boolean> = validateUserForCreateOrUpdate(
        command.id,
        tenantId,
        command.accountName,
        command.email,
        command.phoneNumber
    ).flatMap {
        commandInvoker.dispatch<UpdateUserCommand, Boolean>(command).map { it }
    }

    fun deleteUser(command: DeleteUserCommand, tenantId: String): Uni<Boolean> =
        commandInvoker.dispatch<DeleteUserCommand, Boolean>(command).map { it }

    private fun validateUserForCreateOrUpdate(
        id: String?,
        tenantId: String,
        accountName: String,
        email: String?,
        phoneNumber: String?
    ): Uni<Unit> = validateUserExists(id, tenantId)
        .flatMap { validateAccountNameUnique(id, tenantId, accountName) }
        .flatMap { validateEmailUnique(id, tenantId, email) }
        .flatMap { validatePhoneNumberUnique(id, tenantId, phoneNumber) }

    private fun validateUserExists(id: String?, tenantId: String): Uni<Unit> = when (id) {
        null -> Uni.createFrom().item(Unit)
        else -> userQueryService.handle(UserById(id, tenantId))
            .onItem().ifNull().failWith(ServiceException(ErrorCode.ACCOUNT_NOT_FOUND))
            .replaceWithUnit()
    }

    private fun validateAccountNameUnique(id: String?, tenantId: String, accountName: String?): Uni<Unit> = when {
        accountName.isNullOrBlank() -> Uni.createFrom().item(Unit)
        else -> userQueryService.handle(UserByaAccountName(accountName, tenantId))
            .flatMap { user ->
                when {
                    user != null && (id == null || user.id != id) -> Uni.createFrom()
                        .failure(ServiceException(ErrorCode.ACCOUNT_NAME_EXISTS))

                    else -> Uni.createFrom().item(Unit)
                }
            }
    }

    private fun validateEmailUnique(id: String?, tenantId: String, email: String?): Uni<Unit> = when {
        email.isNullOrBlank() -> Uni.createFrom().item(Unit)
        else -> userQueryService.handle(UserByEmail(email, tenantId))
            .flatMap { user ->
                when {
                    user != null && (id == null || user.id != id) -> Uni.createFrom()
                        .failure(ServiceException(ErrorCode.ACCOUNT_EMAIL_EXISTS))

                    else -> Uni.createFrom().item(Unit)
                }
            }
    }

    private fun validatePhoneNumberUnique(id: String?, tenantId: String, phoneNumber: String?): Uni<Unit> = when {
        phoneNumber.isNullOrBlank() -> Uni.createFrom().item(Unit)
        else -> userQueryService.handle(UserByPhoneNumber(phoneNumber, tenantId))
            .flatMap { user ->
                when {
                    user != null && (id == null || user.id != id) -> Uni.createFrom()
                        .failure(ServiceException(ErrorCode.ACCOUNT_PHONE_NUMBER_EXISTS))

                    else -> Uni.createFrom().item(Unit)
                }
            }
    }
}