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
    val logResBody: Boolean = false
)
