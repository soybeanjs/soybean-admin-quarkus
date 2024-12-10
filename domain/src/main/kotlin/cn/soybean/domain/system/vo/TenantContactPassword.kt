/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.vo

object TenantContactPassword {
    fun genPass(name: String): String = "$name@123456."
}
