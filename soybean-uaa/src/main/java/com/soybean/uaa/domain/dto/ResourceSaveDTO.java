package com.soybean.uaa.domain.dto;

import com.soybean.uaa.domain.enums.ResourceType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 资源
 * </p>
 *
 * @author wenxina
 * @since 2019-11-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class ResourceSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "名称不能为空")
    @Length(max = 20, message = "名称长度不能超过20")
    private String label;
    /**
     * 描述
     */
    @Length(max = 200, message = "描述长度不能超过200")
    private String description;
    /**
     * 是否公开菜单
     * 就是无需分配就可以访问的。所有人可见
     */
    private Boolean global;
    private Boolean locked;
    /**
     * 路径
     */
    @Length(max = 255, message = "路径长度不能超过255")
    private String path;
    /**
     * 组件
     */
    @Length(max = 255, message = "组件长度不能超过255")
    private String component;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 排序
     */
    private Integer sequence;
    /**
     * 菜单图标
     */
    @Length(max = 255, message = "菜单图标长度不能超过255")
    private String icon;
    /**
     * 父级菜单id
     */
    @NotNull(message = "父节点不能为空")
    private Long parentId;
    /**
     * 资源编码
     */
    @Length(max = 100, message = "资源编码长度不能超过{max}")
    private String permission;

    private String model;

    private ResourceType type;

}
