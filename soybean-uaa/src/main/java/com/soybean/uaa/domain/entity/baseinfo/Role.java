package com.soybean.uaa.domain.entity.baseinfo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.soybean.framework.commons.entity.Entity;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScopeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxina
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class Role extends Entity<Long> {

    private Long tenantId;

    private String name;

    private String code;

    @TableField("`super`")
    private Boolean superRole;

    private String description;

    private Boolean readonly;

    private Boolean locked;
    /**
     * 数据权限类型
     * #DataScopeType
     */
    private DataScopeType scopeType;
}
