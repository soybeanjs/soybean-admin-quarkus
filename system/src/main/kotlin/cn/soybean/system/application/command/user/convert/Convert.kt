package cn.soybean.system.application.command.user.convert

import cn.soybean.domain.system.event.user.UserCreatedOrUpdatedEventBase
import cn.soybean.system.application.command.user.CreateUserCommand

fun CreateUserCommand.convert2UserCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    createBy: String,
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
        avatar = avatar,
        deptId = deptId,
        status = status
    ).also {
        it.tenantId = tenantId
        it.createBy = createBy
        it.createAccountName = createAccountName
    }
