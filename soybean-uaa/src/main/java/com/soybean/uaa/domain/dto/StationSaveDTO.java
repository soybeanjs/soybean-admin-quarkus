package com.soybean.uaa.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 岗位
 * </p>
 *
 * @author wenxina
 * @since 20122-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class StationSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Length(max = 255, message = "岗位名称长度不能超过{max}")
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    /**
     * 岗位编码
     */
    private String code;


    /**
     * 岗位类型
     */
    @NotNull(message = "岗位类型不能为空")
    private Integer type;

    private Integer sequence;

    /**
     * 组织ID
     * #c_core_org
     */
    @NotNull(message = "组织不能为空")
    private Long orgId;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度不能超过255")
    private String description;

}
