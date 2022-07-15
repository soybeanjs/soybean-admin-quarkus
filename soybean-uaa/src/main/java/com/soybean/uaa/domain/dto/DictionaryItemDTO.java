package com.soybean.uaa.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author wenxina
 */
@Data
public class DictionaryItemDTO {

    /**
     * 编码
     * 一颗树仅仅有一个统一的编码
     */
    @NotBlank(message = "值不能为空")
    @Length(max = 64, message = "值的长度不能超过{max}")
    private String value;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Length(max = 64, message = "名称长度不能超过{max}")
    private String label;

    /**
     * 描述
     */
    @Length(max = 200, message = "描述长度不能超过{max}")
    private String description;

    @Length(max = 20, message = "颜色长度不能超过{max}")
    private String color;
    /**
     * 状态
     */
    private Boolean status;


}
