/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.query.user

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.interfaces.rest.dto.request.BasePageParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

class UserQuery(
    @field:Parameter(description = "账户名")
    @field:QueryParam("accountName")
    var accountName: String? = null,
    @field:Parameter(description = "性别")
    @field:QueryParam("gender")
    var gender: DbEnums.Gender? = null,
    @field:Parameter(description = "昵称")
    @field:QueryParam("nickName")
    var nickName: String? = null,
    @field:Parameter(description = "手机号")
    @field:QueryParam("phoneNumber")
    var phoneNumber: String? = null,
    @field:Parameter(description = "电子邮箱")
    @field:QueryParam("email")
    var email: String? = null,
    @field:Parameter(description = "用户状态")
    @field:QueryParam("status")
    var status: DbEnums.Status? = null,
) : BasePageParam()
