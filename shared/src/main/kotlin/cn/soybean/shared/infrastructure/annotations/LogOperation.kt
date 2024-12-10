/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.infrastructure.annotations

import jakarta.interceptor.InterceptorBinding

@InterceptorBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class LogOperation(
    val moduleName: String = "",
    val description: String = "",
    val logReqParams: Boolean = false,
    val logReqBody: Boolean = false,
    val logResBody: Boolean = false,
)
