package cn.soybean.framework.common.consts

object AppConstants {
    const val API_KEY = "X-Api-Key"
    const val API_SECRET = "X-Api-Secret"
    const val API_ALGORITHM = "X-Api-Algorithm"
    const val API_SIGNATURE = "X-Api-Signature"
    const val API_TIMESTAMP = "X-Api-Timestamp"
    const val API_NONCE = "X-Api-Nonce"
    const val API_TENANT_ID = "X-Tenant-Id"
    const val API_TIMESTAMP_DISPARITY: Long = 10 * 60 * 1000
    const val API_TIMESTAMP_EXTRA_TIME_MARGIN: Long = 2 * 60 * 1000
    const val API_NONCE_CACHE_PREFIX = "CACHE::API_NONCE"
    const val APP_COMMON_ROLE = "SYSTEM_BACKEND_USER"
    private const val APP_PERM_PREFIX = "permissions"
    const val APP_PERM_NAME_PREFIX = "${APP_PERM_PREFIX}-"
    const val APP_PERM_ACTION_PREFIX = "${APP_PERM_PREFIX}:"
    const val APP_PERM_ACTION_CACHE_PREFIX = "CACHE::APP_PERM_ACTION"
}