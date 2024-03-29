package cn.soybean.system.domain.entity

import cn.soybean.domain.model.BaseTenantEntity
import cn.soybean.infrastructure.config.consts.DbConstants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_dept", indexes = [
        Index(columnList = "tenant_id")
    ]
)
class SystemDeptEntity(

    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false, length = DbConstants.LENGTH_20)
    var name: String? = null,

    /**
     * 父部门ID
     */
    @Column(name = "parent_id")
    var parentId: Long? = null,

    /**
     * 排序
     */
    @Column(name = "sequence")
    var order: Int? = null,

    /**
     * 部门领导ID
     */
    @Column(name = "leader_user_id")
    var leaderUserId: Long? = null,

    /**
     * 部门领导账号名称
     */
    @Column(name = "leader_account_name", length = DbConstants.LENGTH_20)
    var leaderAccountName: String? = null,

    @Column(name = "remark")
    var remark: String? = null
) : BaseTenantEntity()