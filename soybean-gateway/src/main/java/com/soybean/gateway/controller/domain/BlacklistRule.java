package com.soybean.gateway.controller.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 黑名单规则
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Data
public class BlacklistRule {

    private String id;
    private String ip;
    private Long visits;
    private String method;
    private String path;
    private Boolean status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private LocalDateTime createdTime;
}
