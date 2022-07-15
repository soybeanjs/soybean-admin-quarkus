package com.soybean.uaa.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 实体类
 * 角色的资源
 * </p>
 *
 * @author wenxina
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_res")
@Builder
public class RoleRes {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     * #c_auth_resource
     * #c_auth_menu
     */
    @NotNull(message = "资源id不能为空")
    @TableField("res_id")
    private Long resId;


    /**
     * 角色id
     * #t_sys_role
     */
    @NotNull(message = "角色id不能为空")
    @TableField("role_id")
    private Long roleId;

}
