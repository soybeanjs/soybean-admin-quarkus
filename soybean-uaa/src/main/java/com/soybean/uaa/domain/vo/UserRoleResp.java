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
public class UserRoleResp {

    private List<UserRoleDetail> userRoleDetails;
    private List<Long> originTargetKeys;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRoleDetail {
        private Long id;
        private String nickName;
        private String username;

    }

}
