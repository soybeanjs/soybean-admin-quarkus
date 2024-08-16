package cn.soybean.system.interfaces.rest.dto.response.user

import cn.soybean.domain.system.enums.DbEnums

data class UserResponse(
    var id: String? = null,
    var accountName: String? = null,
    var nickName: String? = null,
    var personalProfile: String? = null,
    var email: String? = null,
    var countryCode: String? = null,
    var phoneCode: String? = null,
    var phoneNumber: String? = null,
    var gender: DbEnums.Gender? = null,
    var avatar: String? = null,
    var deptId: String? = null,
    var status: DbEnums.Status? = null
)
