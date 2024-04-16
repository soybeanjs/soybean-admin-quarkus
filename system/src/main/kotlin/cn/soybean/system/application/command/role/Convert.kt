package cn.soybean.system.application.command.role

import cn.soybean.system.domain.event.role.RoleCreatedOrUpdatedEventBase

fun CreateRoleCommand.toRoleCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    userId: String,
    accountName: String
): RoleCreatedOrUpdatedEventBase =
    RoleCreatedOrUpdatedEventBase(
        aggregateId = id,
        name = name,
        code = code,
        order = order,
        status = status,
        dataScope = dataScope,
        dataScopeDeptIds = dataScopeDeptIds,
        remark = remark
    ).also {
        it.tenantId = tenantId
        it.createBy = userId
        it.createAccountName = accountName
    }

