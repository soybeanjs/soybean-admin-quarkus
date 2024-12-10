/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.tenant.convert

import cn.soybean.domain.system.event.tenant.TenantCreatedEventBase
import cn.soybean.system.application.command.tenant.CreateTenantCommand

fun CreateTenantCommand.convert2TenantCreatedOrUpdatedEventBase(
    id: String,
    tenantId: String,
    createBy: String,
    createAccountName: String,
    contactUserId: String,
): TenantCreatedEventBase = TenantCreatedEventBase(
    aggregateId = id,
    name = name,
    contactUserId = contactUserId,
    contactAccountName = contactAccountName,
    status = status,
    website = website,
    expireTime = expireTime,
    menuIds = menuIds,
    operationIds = operationIds,
).also {
    it.tenantId = tenantId
    it.createBy = createBy
    it.createAccountName = createAccountName
}
