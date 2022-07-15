package com.soybean.uaa.domain.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 角色的资源
 * </p>
 *
 * @author wenxina
 * @since 2019-07-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RoleResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     * #c_auth_menu
     */
    private List<Long> menuIdList;

    /**
     * 资源id
     * #c_auth_resource
     */
    private List<Long> resourceIdList;

}
