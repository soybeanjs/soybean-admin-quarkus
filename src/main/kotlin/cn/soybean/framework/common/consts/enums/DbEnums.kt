package cn.soybean.framework.common.consts.enums

object DbEnums {
    enum class DataPermission {
        ALL_DATA_PERMISSIONS, // 全部数据权限
        SPECIFIC_DEPARTMENT, // 指定部门数据权限
        DEPARTMENT_PERMISSIONS, // 部门数据权限
        DEPARTMENT_AND_SUB_PERMISSIONS, // 部门及以下数据权限
        SELF_ONLY, // 仅本人数据权限
        CUSTOM // 用户自定义数据权限，可能针对特定的业务需求定义更细粒度的权限
    }

    enum class MenuItemType {
        MENU, // 表示一个具体的菜单项
        DIRECTORY // 表示一个包含子菜单项的目录
    }

    enum class Gender {
        MALE, FEMALE, OTHER
    }

    enum class Status {
        ENABLED, DISABLED
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