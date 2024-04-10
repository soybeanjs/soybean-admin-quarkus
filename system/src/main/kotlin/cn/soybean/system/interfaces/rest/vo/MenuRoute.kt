package cn.soybean.system.interfaces.rest.vo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class MenuRoute(
    @JsonIgnore
    val parentId: String? = null,
    val name: String? = null,
    val path: String? = null,
    val component: String? = null,
    val meta: RouteMeta? = null,
    var children: List<MenuRoute>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class RouteMeta(
    val title: String? = null,
    val i18nKey: String? = null,
    val roles: List<String>? = null,
    val keepAlive: Boolean? = null,
    val constant: Boolean? = null,
    val icon: String? = null,
    val order: Int? = null,
    val href: String? = null,
    val hideInMenu: Boolean? = null,
    val activeMenu: String? = null,
    val multiTab: Boolean? = null,
)