package cn.soybean.shared.infrastructure.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ApiKeyRequest(val keyName: String = "X-Api-Key")