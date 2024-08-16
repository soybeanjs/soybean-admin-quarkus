package cn.soybean.domain.system.entity

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.domain.system.config.DbConstants
import cn.soybean.domain.system.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_post", indexes = [
        Index(columnList = "tenant_id")
    ]
)
open class SystemPostEntity(

    /**
     * 岗位名称
     */
    @Column(name = "name", nullable = false, length = DbConstants.LENGTH_20)
    var name: String? = null,

    /**
     * 排序
     */
    @Column(name = "sequence")
    var order: Int? = null,

    /**
     * 状态
     */
    @Column(name = "status")
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    @Column(name = "remark")
    var remark: String? = null
) : BaseTenantEntity()
