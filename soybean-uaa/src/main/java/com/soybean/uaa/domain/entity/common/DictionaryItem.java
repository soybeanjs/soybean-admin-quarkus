package com.soybean.uaa.domain.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.soybean.framework.commons.entity.SuperEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 字典项
 * </p>
 *
 * @author wenxina
 * @since 2020-01-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("common_dictionary_item")
public class DictionaryItem extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @TableField("dictionary_id")
    private Long dictionaryId;

    /**
     * 编码
     */
    private String dictionaryCode;

    /**
     * 编码
     */
    @TableField(value = "`value`", condition = LIKE)
    private String value;

    /**
     * 名称
     */
    @TableField(value = "label", condition = LIKE)
    private String label;

    /**
     * 状态
     */
    @TableField("`status`")
    private Boolean status;

    @TableField("`color`")
    private String color;

    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "description", condition = LIKE)
    private String description;

    /**
     * 排序
     */
    @TableField("`sequence`")
    private Integer sequence;


}
