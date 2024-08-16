package cn.soybean.system.application.command.user

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.event.user.UserCreatedOrUpdatedEventBase
import cn.soybean.shared.application.command.Command
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    UserCreatedOrUpdatedEventBase::class,
    mappings = [
        Mapping(source = "id", target = "aggregateId")
    ]
)
data class UpdateUserCommand(
    var id: String,
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
