package com.soybean.framework.db.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wenxina
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseDictionary {

    private String code;

    private String desc;

}
