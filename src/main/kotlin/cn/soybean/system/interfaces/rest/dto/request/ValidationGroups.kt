package cn.soybean.system.interfaces.rest.dto.request

/**
 * [issue](https://github.com/quarkusio/quarkus/issues/20395)
 * [issue](https://youtrack.jetbrains.com/issue/KT-13228)
 */
interface ValidationGroups {
    interface OnCreate
    interface OnUpdate
    interface OnDelete
}