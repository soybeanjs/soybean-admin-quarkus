package cn.soybean.system.interfaces.rest.dto.response.auth

data class LoginResponse(val token: String, val refreshToken: String)