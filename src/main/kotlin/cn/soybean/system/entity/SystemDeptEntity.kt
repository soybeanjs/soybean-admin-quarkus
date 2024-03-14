package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_dept")
class SystemDeptEntity(

    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false)
    var name: String? = null,

    /**
     * 父部门ID
     */
    @Column(name = "parent_id")
    var parentId: Long? = null,

    /**
     * 排序
     */
    @Column(name = "order")
    var order: Int? = null,

    /**
     * 部门领导ID
     */
    @Column(name = "leader_user_id")
    var leaderUserId: Long? = null,

    /**
     * 部门领导账号名称
     */
    @Column(name = "leader_account_name")
    var leaderAccountName: String? = null,

    @Column(name = "remark")
    var remark: String? = null
) : BaseTenantEntity()