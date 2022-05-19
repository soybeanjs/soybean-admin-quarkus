package com.soybean.gateway;

import com.soybean.framework.boot.listener.AppStartupListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * 网关应用程序
 *
 * @author wenxina
 * @date 2022/03/22
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public AppStartupListener appStartupListener() {
        return new AppStartupListener();
    }
}
