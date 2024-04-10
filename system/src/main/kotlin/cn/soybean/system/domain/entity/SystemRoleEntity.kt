package cn.soybean.system.domain.entity

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.domain.enums.DbEnums
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.shared.infrastructure.persistence.converters.JsonStringSetTypeHandler
import cn.soybean.system.interfaces.rest.vo.RoleRespVO
import io.mcarle.konvert.api.KonvertTo
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_role", indexes = [
        Index(columnList = "tenant_id"),
        Index(columnList = "code")
    ]
)
@KonvertTo(RoleRespVO::class)
class SystemRoleEntity(

    /**
     * 角色名称
     */
    @Column(name = "name", nullable = false, length = DbConstants.LENGTH_20)
    var name: String? = null,

    /**
     * 角色编码
     */
    @Column(name = "code", nullable = false, length = DbConstants.LENGTH_20)
    var code: String? = null,

    /**
     * 排序
     */
    @Column(name = "sequence", nullable = false)
    var order: Int? = null,

    /**
     * 状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     * 是否内置
     */
    @Column(name = "builtin", nullable = false)
    val builtin: Boolean = false,

    /**
     * 数据权限
     */
    @Column(name = "data_scope")
    var dataScope: DbEnums.DataPermission? = null,

    /**
     * 数据权限部门ID
     */
    @Column(name = "data_scope_dept_ids")
    @Convert(converter = JsonStringSetTypeHandler::class)
    var dataScopeDeptIds: Set<String>? = null,

    @Column(name = "remark")
    var remark: String? = null,
) : BaseTenantEntity()