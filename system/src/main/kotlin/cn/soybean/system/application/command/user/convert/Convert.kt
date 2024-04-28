package cn.soybean.system.application.command.user.convert

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.user.CreateUserCommand
import cn.soybean.system.application.command.user.TenantAssociatesUserCommand
import cn.soybean.system.domain.event.user.UserCreatedOrUpdatedEventBase
import cn.soybean.system.domain.vo.TenantContactPassword

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

fun TenantAssociatesUserCommand.convert2UserCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    contactAccountName: String,
    createBy: String,
    createAccountName: String
): UserCreatedOrUpdatedEventBase =
    UserCreatedOrUpdatedEventBase(
        aggregateId = id,
        accountName = contactAccountName,
        accountPassword = TenantContactPassword.genPass(createAccountName),
        nickName = contactAccountName,
        personalProfile = "system generated",
        countryCode = DbEnums.CountryInfo.CN.countryCode,
        phoneCode = DbEnums.CountryInfo.CN.phoneCode,
        status = DbEnums.Status.ENABLED
    ).also {
        it.tenantId = tenantId
        it.createBy = createBy
        it.createAccountName = createAccountName
    }