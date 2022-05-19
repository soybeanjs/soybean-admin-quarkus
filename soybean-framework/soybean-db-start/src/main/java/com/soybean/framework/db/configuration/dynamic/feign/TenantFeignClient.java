package com.soybean.framework.db.configuration.dynamic.feign;

import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.configuration.dynamic.event.body.TenantDynamicDatasource;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author wenxina
 */
@FeignClient(name = "soybean-uaa", decode404 = true, fallback = TenantFeignClient.TenantFeignClientFallback.class)
public interface TenantFeignClient {

    /**
     * 查询所有租户数据源
     *
     * @return 查询结果
     */
    @GetMapping("/tenants/databases/active")
    Result<List<TenantDynamicDatasource>> selectAll();

    @Component
    @RequiredArgsConstructor
    class TenantFeignClientFallback implements TenantFeignClient {

        @Override
        public Result<List<TenantDynamicDatasource>> selectAll() {
            return Result.success();
        }
    }
}
