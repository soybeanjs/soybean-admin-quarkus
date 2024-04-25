package cn.soybean.system.interfaces.rest.dto.response.user

data class UserInfoResponse(val userId: String, val userName: String, val roles: Set<Any>)
