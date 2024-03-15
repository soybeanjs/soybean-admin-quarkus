package cn.soybean.framework.common.exception

class ServiceException(errorCode: ErrorCode) : RuntimeException(errorCode.message)

enum class ErrorCode(val code: Int, val message: String) {
    TENANT_NOT_FOUND(1001, "The tenant does not exist."),
    TENANT_EXPIRED(1002, "The tenant has expired."),
    TENANT_DISABLED(1003, "The tenant has been disabled."),

    ACCOUNT_NOT_FOUND(2001, "The account does not exist."),
    ACCOUNT_CREDENTIALS_INVALID(2002, "The account credentials are incorrect."),
    ACCOUNT_DISABLED(2003, "The account has been disabled.")
}