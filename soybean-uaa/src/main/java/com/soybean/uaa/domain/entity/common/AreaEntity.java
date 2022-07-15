package com.soybean.uaa.domain.entity.common;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 字典类型
 * </p>
 *
 * @author wenxina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("common_area")
public class AreaEntity {

    @TableId(value = "id", type = IdType.INPUT)
    @NotNull(message = "id不能为空")
    protected Long id;
    /**
     * 名称
     */
    protected String name;
    /**
     * 父ID
     */
    protected Long parentId;
    /**
     * 创建人ID
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected Long createdBy;
    /**
     * 创建人名称
     */
    @TableField(value = "created_name", fill = FieldFill.INSERT)
    protected String createdName;
    /**
     * 创建时间（依托数据库功能）
     */
    @TableField(value = "created_time")
    protected LocalDateTime createdTime;
    @TableField(value = "last_modified_time")
    protected LocalDateTime lastModifiedTime;
    @TableField(value = "last_modified_by", fill = FieldFill.INSERT_UPDATE)
    protected Long lastModifiedBy;
    @TableField(value = "last_modified_name", fill = FieldFill.INSERT_UPDATE)
    protected String lastModifiedName;
    @TableField("`level`")
    private Integer level;
    /**
     * 经度
     */
    private BigDecimal longitude;
    @TableField("`sequence`")
    private Integer sequence;
    @TableField("`source`")
    private String source;
    /**
     * 纬度
     */
    private BigDecimal latitude;

}
