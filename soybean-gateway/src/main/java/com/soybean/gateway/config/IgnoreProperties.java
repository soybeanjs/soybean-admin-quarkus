package com.soybean.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 忽略属性
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Data
@ConfigurationProperties(prefix = "ignore")
public class IgnoreProperties {

    private List<String> clients = new ArrayList<>();
    private List<String> swaggerProviders = new ArrayList<>();

}
