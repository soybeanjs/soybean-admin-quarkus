package com.soybean.framework.db.page;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soybean.framework.commons.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数
 *
 * @author wenxina
 * @since 2020-07-08
 */
@Slf4j
@Data
public class PageRequest {

    private long current = 1;

    private long size = 20;

    private String column;

    private Boolean asc = true;


    @JsonIgnore
    public <T> Page<T> buildPage() {
        PageRequest params = this;
        if (StringUtils.isBlank(params.getColumn())) {
            return new Page<>(params.getCurrent(), params.getSize());
        }
        Page<T> page = new Page<>(params.getCurrent(), params.getSize());
        List<OrderItem> orders = new ArrayList<>();
        // 简单的 驼峰 转 下划线
        String column = StrUtil.toUnderlineCase(params.getColumn());
        orders.add(params.getAsc() ? OrderItem.asc(column) : OrderItem.desc(column));
        page.setOrders(orders);
        return page;
    }
}
