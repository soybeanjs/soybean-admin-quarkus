package cn.soybean.system.application.command.role.convert

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.role.CreateRoleCommand
import cn.soybean.system.application.command.role.TenantAssociatesRoleCommand
import cn.soybean.system.domain.config.DbConstants
import cn.soybean.system.domain.event.role.RoleCreatedOrUpdatedEventBase

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

fun TenantAssociatesRoleCommand.convert2RoleCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    createBy: String,
    createAccountName: String
): RoleCreatedOrUpdatedEventBase =
    RoleCreatedOrUpdatedEventBase(
        aggregateId = id,
        name = DbConstants.SUPER_TENANT_ROLE_CODE,
        code = DbConstants.SUPER_TENANT_ROLE_CODE,
        order = 1,
        status = DbEnums.Status.ENABLED,
        remark = "system generated"
    ).also {
        it.tenantId = tenantId
        it.createBy = createBy
        it.createAccountName = createAccountName
    }
