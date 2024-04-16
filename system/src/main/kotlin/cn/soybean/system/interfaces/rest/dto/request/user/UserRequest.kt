package cn.soybean.system.interfaces.rest.dto.request.user

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.user.CreateUserCommand
import cn.soybean.system.application.command.user.UpdateUserCommand
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null

@KonvertTo(
    CreateUserCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
@KonvertTo(
    UpdateUserCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class UserRequest(
    @field:Null(groups = [ValidationGroups.OnCreate::class])
    @field:NotNull(groups = [ValidationGroups.OnUpdate::class])
    var id: String? = null,

    @field:NotBlank
    var accountName: String? = null,

    @field:NotBlank
    var accountPassword: String? = null,

    @field:NotBlank
    var nickName: String? = null,

    var personalProfile: String? = null,

    var email: String? = null,

    var countryCode: String = DbEnums.CountryInfo.CN.countryCode,

    var phoneCode: String = DbEnums.CountryInfo.CN.phoneCode,

    var phoneNumber: String? = null,

    var gender: DbEnums.Gender? = null,

    var avatar: String? = null,

    var deptId: String? = null,

    var status: DbEnums.Status = DbEnums.Status.ENABLED
)