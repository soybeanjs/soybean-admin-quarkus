package cn.soybean.system.application.command

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command
import cn.soybean.system.domain.event.UserCreatedOrUpdatedEventBase
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

data class CreateUserCommand(
    var accountName: String,
    var accountPassword: String,
    var nickName: String,
    var personalProfile: String? = null,
    var email: String? = null,
    var countryCode: String,
    var phoneCode: String,
    var phoneNumber: String? = null,
    var gender: DbEnums.Gender? = null,
    var avatar: String? = null,
    var deptId: String? = null,
    var status: DbEnums.Status
) : Command

fun CreateUserCommand.toUserCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    userId: String,
    createAccountName: String
): UserCreatedOrUpdatedEventBase =
    UserCreatedOrUpdatedEventBase(
        aggregateId = id,
        accountName = accountName,
        accountPassword = accountPassword,
        nickName = nickName,
        personalProfile = personalProfile,
        email = email,
        countryCode = countryCode,
        phoneCode = phoneCode,
        phoneNumber = phoneNumber,
        gender = gender,
        avatar = phoneNumber,
        deptId = deptId,
        status = status
    ).also {
        it.tenantId = tenantId
        it.createBy = userId
        it.createAccountName = createAccountName
    }

@KonvertTo(
    UserCreatedOrUpdatedEventBase::class,
    mappings = [
        Mapping(source = "id", target = "aggregateId")
    ]
)
data class UpdateUserCommand(
    var id: String,
    var accountName: String,
    var accountPassword: String,
    var nickName: String,
    var personalProfile: String? = null,
    var email: String? = null,
    var countryCode: String,
    var phoneCode: String,
    var phoneNumber: String? = null,
    var gender: DbEnums.Gender? = null,
    var avatar: String? = null,
    var deptId: String? = null,
    var status: DbEnums.Status
) : Command

data class DeleteUserCommand(val ids: Set<String>) : Command