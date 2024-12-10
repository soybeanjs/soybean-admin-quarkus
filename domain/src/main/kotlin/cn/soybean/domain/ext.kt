/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain

import cn.soybean.domain.system.config.DbConstants

fun isSuperUser(userId: String): Boolean = userId == DbConstants.SUPER_USER

fun isSuperRole(roleId: String): Boolean = roleId == DbConstants.SUPER_SYSTEM_ROLE_ID
