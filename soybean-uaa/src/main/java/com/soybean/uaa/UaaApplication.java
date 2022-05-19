package com.soybean.uaa;

import com.soybean.framework.boot.listener.AppStartupListener;
import com.soybean.framework.boot.log.event.SysLogListener;
import com.soybean.framework.security.client.annotation.EnableOauth2ClientResourceServer;
import com.soybean.uaa.service.OptLogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Repository;

/**
 * @author wenxina
 */
@Slf4j
@EnableCaching
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.soybean")
@MapperScan(value = "com.soybean.uaa.repository", annotationClass = Repository.class)
@EnableOauth2ClientResourceServer
public class UaaApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService) {
        return new SysLogListener(optLogService::save);
    }

    @Bean
    public AppStartupListener appStartupListener() {
        return new AppStartupListener();
    }

}
