package cn.soybean.system.application.command.user

import cn.soybean.system.domain.event.user.UserCreatedOrUpdatedEventBase

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

