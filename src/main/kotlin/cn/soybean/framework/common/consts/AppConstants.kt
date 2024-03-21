package cn.soybean.framework.common.consts

object AppConstants {
    const val API_KEY = "X-Api-Key"
    const val API_SECRET = "X-Api-Secret"
    const val API_ALGORITHM = "X-Api-Algorithm"
    const val API_SIGNATURE = "X-Api-Signature"
    const val API_TIMESTAMP = "X-Api-Timestamp"
    const val API_NONCE = "X-Api-Nonce"
    const val API_TIMESTAMP_DISPARITY: Long = 10 * 60 * 1000
    const val API_TIMESTAMP_EXTRA_TIME_MARGIN: Long = 2 * 60 * 1000
    const val API_NONCE_CACHE_PREFIX = "CACHE::API_NONCE"
}