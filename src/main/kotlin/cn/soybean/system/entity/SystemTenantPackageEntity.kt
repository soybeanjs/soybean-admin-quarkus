package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.common.converters.JsonLongSetTypeHandler
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_tenant_package")
class SystemTenantPackageEntity(

    /**
     * 租户套餐名称
     */
    @Column(name = "name", nullable = false)
    var name: String? = null,

    /**
     * 租户套餐状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     * 备注
     */
    @Column(name = "remark")
    var remark: String? = null,

    /**
     * 租户套餐菜单编号集合
     */
    @Column(name = "menu_ids")
    @Convert(converter = JsonLongSetTypeHandler::class)
    var menuIds: Set<Long>? = null
) : BaseEntity()