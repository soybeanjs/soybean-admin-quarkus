package cn.soybean.system.interfaces.rest.dto.request.route

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.application.command.route.CreateRouteCommand
import cn.soybean.system.application.command.route.UpdateRouteCommand
import cn.soybean.system.interfaces.rest.dto.request.ValidationGroups
import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.KonvertTo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null

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
    var id: String? = null,

    @field:NotBlank
    var menuName: String? = null,

    @field:NotNull
    var menuType: DbEnums.MenuItemType? = null,

    @field:NotNull
    var order: Int? = null,

    @field:NotNull
    var parentId: String? = null,

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

    @field:NotNull
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    var roles: List<String>? = null,

    var keepAlive: Boolean? = null,

    var constant: Boolean? = null,

    var href: String? = null
)