package com.soybean.framework.db.configuration.permission.feign;

import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wenxina
 */
@FeignClient(name = "soybean-uaa", decode404 = true, fallback = DataScopeFeignClient.DataScopeClientFallback.class)
public interface DataScopeFeignClient {

    /**
     * 通过id获取数据范围
     *
     * @param userId 用户id
     * @return 查询结果
     */
    @GetMapping(value = "/getDataScopeById", headers = {"rewrite=0"})
    DataScope getDataScopeById(@RequestParam("userId") Long userId);

    @Component
    @RequiredArgsConstructor
    class DataScopeClientFallback implements DataScopeFeignClient {

        @Override
        public DataScope getDataScopeById(Long userId) {
            return DataScope.builder().build();
        }
    }
}
