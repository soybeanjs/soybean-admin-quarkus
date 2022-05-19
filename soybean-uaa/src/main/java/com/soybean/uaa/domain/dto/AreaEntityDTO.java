package com.soybean.uaa.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author wenxina
 */
@Data
public class AreaEntityDTO {

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    protected String name;
    /**
     * 父ID
     */
    @NotNull(message = "上级国标码不能为空")
    protected Long parentId;
    @NotNull(message = "国标码不能为空")
    private Long id;
    private Integer level;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    private Integer sequence;
    @Length(max = 255, message = "数据长度不能超过 {max}")
    private String source;


}
