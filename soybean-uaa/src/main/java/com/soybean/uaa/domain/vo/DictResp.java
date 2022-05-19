package com.soybean.uaa.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wenxina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictResp {

    private Long value;
    private String label;

}
