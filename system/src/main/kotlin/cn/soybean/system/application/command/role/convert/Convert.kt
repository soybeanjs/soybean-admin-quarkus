package cn.soybean.system.application.command.role.convert

import cn.soybean.domain.system.event.role.RoleCreatedOrUpdatedEventBase
import cn.soybean.system.application.command.role.CreateRoleCommand

fun CreateRoleCommand.convert2RoleCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    createBy: String,
    createAccountName: String
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
        it.createBy = createBy
        it.createAccountName = createAccountName
    }
