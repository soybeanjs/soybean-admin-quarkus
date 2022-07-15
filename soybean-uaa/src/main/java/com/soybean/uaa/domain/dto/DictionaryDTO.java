package com.soybean.uaa.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author wenxina
 */
@Data
public class DictionaryDTO {

    /**
     * 编码
     * 一颗树仅仅有一个统一的编码
     */
    @NotBlank(message = "编码不能为空")
    @Length(max = 64, message = "类型长度不能超过64")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Length(max = 64, message = "名称长度不能超过64")
    private String name;

    /**
     * 描述
     */
    @Length(max = 200, message = "描述长度不能超过200")
    private String description;

    /**
     * 状态
     */
    private Boolean status;

    private Integer sequence;


}
