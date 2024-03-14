package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_post")
class SystemPostEntity(

    /**
     * 岗位名称
     */
    @Column(name = "name", nullable = false)
    var name: String? = null,

    /**
     * 排序
     */
    @Column(name = "order")
    var order: Int? = null,

    /**
     * 状态
     */
    @Column(name = "status")
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    @Column(name = "remark")
    var remark: String? = null
) : BaseTenantEntity()