package com.soybean.uaa.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 角色分配
 * 账号角色绑定
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
public class UserRoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     * #c_auth_role
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    /**
     * 用户ID
     * #c_core_accou
     */
    @Size(min = 1, message = "用户不能为空")
    private List<Long> userIdList;

}
