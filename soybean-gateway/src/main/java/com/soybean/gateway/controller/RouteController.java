package com.soybean.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.soybean.framework.commons.entity.Result;
import com.soybean.gateway.config.rule.RouteRuleHelper;
import com.soybean.gateway.controller.domain.RouteRule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 路由控制器
 *
 * @author wenxina
 * @date 2022/03/22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway/rules/routes")
public class RouteController {

    private final RouteRuleHelper routeRuleHelper;

    @GetMapping
    public Result<JSONObject> query() {
        JSONObject data = new JSONObject();
        final List<RouteRule> limitRules = routeRuleHelper.query();
        data.put("total", limitRules.size());
        data.put("records", limitRules);
        data.put("current", 1);
        data.put("size", 20);
        data.put("pages", 1);
        return Result.success(data);
    }

    @SneakyThrows
    @PostMapping
    public Result<Void> add(@Validated @RequestBody RouteRule rule) {
        routeRuleHelper.saveOrUpdate(rule);
        return Result.success();
    }

    @PatchMapping("/{id}/{status}")
    public Result<Void> status(@PathVariable String id, @PathVariable Boolean status) {
        this.routeRuleHelper.publish(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        this.routeRuleHelper.delete(id);
        return Result.success();
    }

}
