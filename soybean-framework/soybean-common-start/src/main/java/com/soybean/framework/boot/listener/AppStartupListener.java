package com.soybean.framework.boot.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;

/**
 * 应用程序启动监听器
 *
 * @author wenxina
 * @date 2022/03/11
 */
@Slf4j
@Component
public class AppStartupListener implements ApplicationRunner {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String appName = applicationContext.getEnvironment().getProperty("spring.application.name");
        String appPort = applicationContext.getEnvironment().getProperty("server.port");
        String appJvmName = ManagementFactory.getRuntimeMXBean().getName();
        String appHost = InetAddress.getLocalHost().getHostAddress();
        String appStartupDate = DateUtil.now();
        String appPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");
        String appInfo = "\n" +
                "================================platform info start================================\n" +
                "\tapplication.name:\t\t\t{}\n" +
                "\tapplication.pid:\t\t\t{}\n" +
                "\tapplication.startTime:\t\tstarted at {}\n" +
                "\tapplication.url:\t\t\thttp://{}:{}{}\n" +
                "================================platform info  end ================================";
        log.info(appInfo, appName, appJvmName.split("@")[0], appStartupDate, StrUtil.blankToDefault(appHost, "127.0.0.1"), StrUtil.blankToDefault(appPort, "8080"), StrUtil.blankToDefault(appPath, StrUtil.EMPTY));
    }
}
