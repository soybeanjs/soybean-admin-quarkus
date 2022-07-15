package com.soybean.uaa.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 实体类
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author wenxina
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_role")
@Builder
public class UserRole {

    /**
     * 角色ID
     * #c_auth_role
     */
    @NotNull(message = "角色ID不能为空")
    @TableField("role_id")
    private Long roleId;

    /**
     * 用户ID
     * #c_core_accou
     */
    @NotNull(message = "用户ID不能为空")
    @TableField("user_id")
    private Long userId;


}
