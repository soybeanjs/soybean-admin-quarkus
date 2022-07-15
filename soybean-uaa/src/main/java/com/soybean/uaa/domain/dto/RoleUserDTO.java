package com.soybean.uaa.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
public class RoleUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private List<Long> userIdList;
    /**
     * 角色id
     * #c_auth_role
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

}
