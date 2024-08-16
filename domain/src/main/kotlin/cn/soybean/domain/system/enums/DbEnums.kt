package cn.soybean.domain.system.enums

import org.eclipse.microprofile.openapi.annotations.media.Schema

object DbEnums {
    @Schema(
        description = "Defines the data permission levels. " +
            "ALL_DATA_PERMISSIONS: Full access to all data; " +
            "SPECIFIC_DEPARTMENT: Access to specific department's data; " +
            "DEPARTMENT_PERMISSIONS: Access to own department's data; " +
            "DEPARTMENT_AND_SUB_PERMISSIONS: Access to own department's and sub-department's data; " +
            "SELF_ONLY: Access to own data only; " +
            "CUSTOM: User-defined custom data permissions for specific business needs."
    )
    enum class DataPermission {
        ALL_DATA_PERMISSIONS, // 全部数据权限
        SPECIFIC_DEPARTMENT, // 指定部门数据权限
        DEPARTMENT_PERMISSIONS, // 部门数据权限
        DEPARTMENT_AND_SUB_PERMISSIONS, // 部门及以下数据权限
        SELF_ONLY, // 仅本人数据权限
        CUSTOM // 用户自定义数据权限，可能针对特定的业务需求定义更细粒度的权限
    }

    @Schema(
        description = "Defines the types of login methods available. " +
            "PC: Login via PC."
    )
    enum class LoginType {
        PC
    }

    @Schema(
        description = "Defines the types of menu items. " +
            "DIRECTORY: Represents a directory containing sub-menu items; " +
            "MENU: Represents a specific menu item."
    )
    enum class MenuItemType {
        DIRECTORY, // 表示一个包含子菜单项的目录
        MENU // 表示一个具体的菜单项
    }

    @Schema(
        description = "Defines the gender options for a user. " +
            "MALE: Male gender; " +
            "FEMALE: Female gender; " +
            "OTHER: Other gender, not classified as male or female."
    )
    enum class Gender {
        MALE, FEMALE, OTHER
    }

    @Schema(
        description = "Indicates whether a resource is enabled or disabled. " +
            "ENABLED: Indicates the resource is active and enabled; " +
            "DISABLED: Indicates the resource is inactive and disabled."
    )
    enum class Status {
        ENABLED, DISABLED
    }

    @Schema(
        description = "Defines the types of API key authentication methods. " +
            "SIMPLE: API key authentication without signing; " +
            "SIGNED: API key authentication requiring a signature, mutually exclusive with SIMPLE."
    )
    enum class ApiKeyType {
        SIMPLE, // API key验证
        SIGNED // API key验证 或者 需要API key和API Secret共同参与的加签验签 互斥的话自己修改filter业务逻辑
    }

    enum class CountryInfo(
        val englishName: String,
        val chineseName: String,
        val countryCode: String,
        val phoneCode: String
    ) {
        CN("China", "中国", "CN", "86"),
        US("United States", "美国", "US", "1")
    }
}
