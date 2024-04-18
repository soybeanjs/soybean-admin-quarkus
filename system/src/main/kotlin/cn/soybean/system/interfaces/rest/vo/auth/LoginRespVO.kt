package cn.soybean.system.interfaces.rest.vo.auth

data class LoginRespVO(val token: String, val refreshToken: String)