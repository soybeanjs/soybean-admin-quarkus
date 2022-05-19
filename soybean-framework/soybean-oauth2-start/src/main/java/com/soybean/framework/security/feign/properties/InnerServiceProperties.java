package com.soybean.framework.security.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author wenxina
 */
@Data
@ConfigurationProperties(prefix = "security.inner.service")
public class InnerServiceProperties {

    /**
     * 安全头
     */
    private String header = "innerRest";
    /**
     * 安全请求头的值
     */
    private String headerValue = "innerFeign";
    /**
     * 白名单
     */
    private List<String> whiteLists;

}
