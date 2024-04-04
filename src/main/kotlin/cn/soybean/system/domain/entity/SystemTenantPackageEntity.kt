package cn.soybean.system.domain.entity

import cn.soybean.domain.enums.DbEnums
import cn.soybean.domain.model.BaseEntity
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.infrastructure.persistence.converters.JsonStringSetTypeHandler
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
    @Column(name = "name", nullable = false, length = DbConstants.LENGTH_20)
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
    @Convert(converter = JsonStringSetTypeHandler::class)
    var menuIds: Set<String>? = null
) : BaseEntity()