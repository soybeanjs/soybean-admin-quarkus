package com.soybean.gateway.controller.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 普遍规则
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Data
public class CommonRule {

    private String id;
    private String method;
    private String path;
    private Boolean status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
