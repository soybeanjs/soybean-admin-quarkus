package com.soybean.uaa.domain.dto;

import com.soybean.framework.db.mybatis.auth.permission.prop.DataScopeType;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 角色
 * </p>
 *
 * @author wenxina
 * @since 2019-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RoleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 描述
     */
    private String describe;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 是否内置角色
     */
    private Boolean readonly;
    /**
     * 数据权限类型
     * #DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}
     */
    private DataScopeType dsType;
    /**
     * 关联的组织id
     */
    private List<Long> orgList;
}
