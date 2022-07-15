package com.soybean.uaa.domain.vo;

import lombok.Data;

/**
 * 构建 Vue路由
 *
 * @author wenxina
 * @since 2019-10-20 15:17
 */
@Data
public class VueRouter {

    private static final long serialVersionUID = -3327478146308500708L;

    private Long id;
    private Long parentId;
    private String path;
    private String name;
    private String label;
    private String icon;
    private String component;
    private String redirect;
    private RouterMeta meta;

    private String model;

    private String permission;
    /**
     * 排序
     */
    private Integer sequence;
    private Integer type;

    private Boolean global;
    private Boolean status;
    private Boolean display;
}
