package cn.soybean.application.exceptions

class ServiceException(errorCode: ErrorCode) : RuntimeException(errorCode.message)