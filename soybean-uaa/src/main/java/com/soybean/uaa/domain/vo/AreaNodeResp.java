package com.soybean.uaa.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wenxina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaNodeResp {

    protected Long parentId;
    private Long value;
    private String label;
    private Integer level;
    private Boolean isLeaf;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;

}
