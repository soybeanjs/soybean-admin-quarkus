package com.soybean.gateway.config.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态路由属性
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class DynamicRouteProperties {

    private DynamicRoute dynamicRoute;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    enum DataType {
        /**
         * redis
         */
        redis,
        /**
         * nacos
         */
        nacos;
        private String desc;

    }

    @Data
    public static class DynamicRoute {
        private Boolean enabled = false;
        private DataType type = DataType.redis;
    }

}
