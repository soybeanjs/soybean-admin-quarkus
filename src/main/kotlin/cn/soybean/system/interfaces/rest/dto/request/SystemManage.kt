package cn.soybean.system.interfaces.rest.dto.request

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.CreateRoleCommand
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.CreateUserCommand
import cn.soybean.system.application.command.UpdateRoleCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.application.command.UpdateUserCommand
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null

@KonvertTo(
    CreateRoleCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
@KonvertTo(
    UpdateRoleCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class RoleRequest(
    @field:Null(groups = [ValidationGroups.OnCreate::class])
    @field:NotNull(groups = [ValidationGroups.OnUpdate::class])
    var id: Long? = null,

    @field:NotBlank
    var name: String? = null,

    @field:NotBlank
    var code: String? = null,

    @field:NotNull
    var order: Int? = null,

    @field:NotNull
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    var dataScope: DbEnums.DataPermission? = null,

    var dataScopeDeptIds: Set<Long>? = null,

    var remark: String? = null
)

@KonvertTo(
    CreateRouteCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
@KonvertTo(
    UpdateRouteCommand::class, options = [
        Konfig(key = "konvert.enforce-not-null", value = "true")
    ]
)
data class RouteRequest(
    @field:Null(groups = [ValidationGroups.OnCreate::class])
    @field:NotNull(groups = [ValidationGroups.OnUpdate::class])
    var id: Long? = null,

    @field:NotBlank
    var menuName: String? = null,

    @field:NotNull
    var menuType: DbEnums.MenuItemType? = null,

    @field:NotNull
    var order: Int? = null,

    @field:NotNull
    var parentId: Long? = null,

    var icon: String? = null,

    var iconType: String? = null,

    @field:NotBlank
    var routeName: String? = null,

    @field:NotBlank
    var routePath: String? = null,

    @field:NotBlank
    var component: String? = null,

    @field:NotBlank
    var i18nKey: String? = null,

    var multiTab: Boolean? = null,

    var activeMenu: String? = null,

    var hideInMenu: Boolean? = null,

    @field:NotBlank
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    var roles: List<String>? = null,

    var keepAlive: Boolean? = null,

    var constant: Boolean? = null,

    var href: String? = null
)

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
    var id: Long? = null,

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

    var deptId: Long? = null,

    var status: DbEnums.Status = DbEnums.Status.ENABLED
)