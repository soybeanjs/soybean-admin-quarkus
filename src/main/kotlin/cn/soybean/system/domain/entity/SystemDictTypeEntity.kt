package cn.soybean.system.domain.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_dict_type", indexes = [
        Index(columnList = "dict_type")
    ]
)
class SystemDictTypeEntity(

    /**
     * 字典名称
     */
    @Column(name = "dict_name", nullable = false, length = DbConstants.LENGTH_20)
    var dictName: String? = null,

    /**
     * 字典类型
     */
    @Column(name = "dict_type", unique = true, nullable = false, length = DbConstants.LENGTH_20)
    var dictType: String? = null,

    /**
     * 状态
     */
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    /**
     * 备注
     */
    var remark: String? = null
) : BaseEntity()
