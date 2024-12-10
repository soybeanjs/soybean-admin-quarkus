/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.permission

import cn.soybean.shared.application.command.Command

data class AuthorizeRoleMenuCommand(
    val roleId: String,
    val menuIds: Set<String>,
) : Command

data class AuthorizeRoleUserCommand(
    val roleId: String,
    val userIds: Set<String>,
) : Command

data class AuthorizeRoleOperationCommand(
    val roleId: String,
    val operationIds: Set<String>,
) : Command
