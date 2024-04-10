package cn.soybean.system.interfaces.rest.vo

import cn.soybean.domain.enums.DbEnums

data class UserRespVO(
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