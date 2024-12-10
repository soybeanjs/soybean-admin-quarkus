/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.response.user

data class UserInfoResponse(
    val userId: String,
    val userName: String,
    val roles: Set<Any>,
)
