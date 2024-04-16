package cn.soybean.system.application.command.user

import cn.soybean.domain.enums.DbEnums
import cn.soybean.shared.application.command.Command

data class CreateUserCommand(
    var accountName: String,
    var accountPassword: String,
    var nickName: String,
    var personalProfile: String? = null,
    var email: String? = null,
    var countryCode: String,
    var phoneCode: String,
    var phoneNumber: String? = null,
    var gender: DbEnums.Gender? = null,
    var avatar: String? = null,
    var deptId: String? = null,
    var status: DbEnums.Status
) : Command