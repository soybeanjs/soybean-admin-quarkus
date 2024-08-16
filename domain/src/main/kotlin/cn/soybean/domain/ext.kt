package cn.soybean.domain

import cn.soybean.domain.system.config.DbConstants

fun isSuperUser(userId: String): Boolean = userId == DbConstants.SUPER_USER
