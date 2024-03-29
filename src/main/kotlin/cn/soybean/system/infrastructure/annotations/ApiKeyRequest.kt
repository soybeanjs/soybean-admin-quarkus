package cn.soybean.system.infrastructure.annotations

import cn.soybean.infrastructure.config.consts.AppConstants

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ApiKeyRequest(val keyName: String = AppConstants.API_KEY)