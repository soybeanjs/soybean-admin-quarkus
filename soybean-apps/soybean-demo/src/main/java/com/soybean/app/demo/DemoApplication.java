package com.soybean.app.demo;

import cn.hutool.core.lang.generator.Generator;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.soybean.framework.boot.listener.AppStartupListener;
import com.soybean.framework.security.client.annotation.EnableOauth2ClientResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * @author wenxina
 */
@EnableOauth2ClientResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.soybean")
@MapperScan(value = "com.soybean.**.mapper", annotationClass = Repository.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public Generator<Long> generator() {
        return new SnowflakeGenerator(1, 1);
    }

    @Bean
    public AppStartupListener appStartupListener() {
        return new AppStartupListener();
    }

}
