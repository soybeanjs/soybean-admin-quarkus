package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_dict_data")
class SystemDictDataEntity(

    /**
     * 排序
     */
    @Column(name = "order", nullable = false)
    var order: Int? = null,

    /**
     * 字典标签
     */
    @Column(name = "label", nullable = false)
    var label: String? = null,

    /**
     * 字典键值
     */
    @Column(name = "value", nullable = false)
    var value: String? = null,

    /**
     *
     */
    @Column(name = "dict_type", nullable = false)
    var dictType: String? = null,

    /**
     * 状态
     */
    @Column(name = "status")
    var status: DbEnums.Status = DbEnums.Status.ENABLED,

    @Column(name = "color")
    var color: String? = null,

    @Column(name = "type")
    var type: String? = null,

    @Column(name = "remark")
    var remark: String? = null
) : BaseEntity()
