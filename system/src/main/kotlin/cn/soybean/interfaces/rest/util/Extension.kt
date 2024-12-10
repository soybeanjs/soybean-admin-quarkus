/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest.util

import cn.soybean.domain.system.config.DbConstants
import io.vertx.core.http.HttpServerRequest

fun getClientIPAndPort(request: HttpServerRequest): Pair<String, Int?> =
    Pair(
        listOf(
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
        ).firstNotNullOfOrNull { header ->
            request
                .getHeader(header)
                ?.split(",")
                ?.first()
                ?.trim()
                ?.split(":")
                ?.first()
                ?.trim()
        } ?: request.remoteAddress().host(),
        request.remoteAddress().port(),
    )

fun isSuperUser(userId: String): Boolean = userId == DbConstants.SUPER_USER
