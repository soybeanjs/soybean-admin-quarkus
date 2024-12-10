package cn.soybean.domain

import cn.soybean.domain.system.config.DbConstants

fun isSuperUser(userId: String): Boolean = userId == DbConstants.SUPER_USER

fun isSuperRole(roleId: String): Boolean = roleId == DbConstants.SUPER_SYSTEM_ROLE_ID
