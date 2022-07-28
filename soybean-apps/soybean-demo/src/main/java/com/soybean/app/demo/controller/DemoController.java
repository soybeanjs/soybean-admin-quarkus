package com.soybean.app.demo.controller;

import com.soybean.app.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenxina
 */
@Slf4j
@RestController
@RequestMapping("/demos")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping
    public void say() {
        log.info("输出内容 - {}", demoService.sayHello());
    }
}
