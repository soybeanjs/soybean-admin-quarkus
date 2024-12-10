/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.config

object DbConstants {
    const val LENGTH_5 = 5
    const val LENGTH_20 = 20
    const val LENGTH_64 = 64
    const val LENGTH_128 = 128
    const val LENGTH_2048 = 2048

    const val PARENT_ID_ROOT = "0"
    const val SUPER_TENANT = "1"
    const val SUPER_USER = "1"
    const val SUPER_SYSTEM_ROLE_ID = "1"
    const val SUPER_SYSTEM_ROLE_CODE = "ROLE_SYSTEM_ADMIN"
    const val SUPER_TENANT_ROLE_CODE = "ROLE_TENANT_ADMIN"
}
