package com.soybean.app.demo.service.impl;

import com.soybean.app.demo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author wenxina
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello() {
        return "hello world";
    }


}
