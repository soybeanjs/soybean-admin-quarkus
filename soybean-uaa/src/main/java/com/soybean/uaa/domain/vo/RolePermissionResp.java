package com.soybean.uaa.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wenxina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResp {

    private List<Long> resIdList;
    private List<VueRouter> buttons;


}
