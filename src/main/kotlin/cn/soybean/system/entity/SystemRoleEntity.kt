package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.common.converters.JsonLongSetTypeHandler
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_role")
class SystemRoleEntity(

    /**
     * 角色名称
     */
    @Column(name = "name", nullable = false)
    var name: String? = null,

    /**
     * 角色编码
     */
    @Column(name = "code", nullable = false)
    var code: String? = null,

    /**
     * 排序
     */
    @Column(name = "order", nullable = false)
    var order: Int? = null,

    /**
     * 状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     *
     */
    @Column(name = "builtin", nullable = false)
    val builtin: Boolean = false,

    /**
     * 数据权限
     */
    @Column(name = "data_scope")
    val dataScope: DbEnums.DataPermission? = null,

    /**
     * 数据权限部门ID
     */
    @Column(name = "data_scope_dept_ids")
    @Convert(converter = JsonLongSetTypeHandler::class)
    val dataScopeDeptIds: Set<Long>? = null,

    @Column(name = "remark")
    var remark: String? = null,
) : BaseTenantEntity()
