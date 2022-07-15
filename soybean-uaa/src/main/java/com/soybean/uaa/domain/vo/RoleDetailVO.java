package com.soybean.uaa.domain.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class RoleDetailVO implements Serializable {

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
     * 是否内置角色
     */
    private Boolean readonly;
    /**
     * 关联的组织id
     */
    private RoleResVO authority;
}
