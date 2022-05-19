package com.soybean.gateway.controller.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 限制规则
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Data
public class LimitRule {

    private String id;
    private Long total;
    private String method;
    private String path;
    private Integer range;
    /**
     * 类型
     */
    private Integer type;
    private Boolean status;
    /**
     * 访问量
     */
    private Long visits;
    /**
     * 进黑名单
     */
    private Boolean blacklist;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private LocalDateTime createdTime;
}
