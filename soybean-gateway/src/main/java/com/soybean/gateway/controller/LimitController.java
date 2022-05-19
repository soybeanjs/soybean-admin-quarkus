package com.soybean.gateway.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.soybean.framework.commons.entity.Result;
import com.soybean.gateway.config.rule.LimitHelper;
import com.soybean.gateway.controller.domain.LimitRule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限制控制器
 *
 * @author wenxina
 * @date 2022/03/22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway/rules/limits")
public class LimitController {

    private final LimitHelper limitHelper;

    @GetMapping
    public Result<JSONObject> query() {
        JSONObject data = new JSONObject();
        final List<LimitRule> limitRules = limitHelper.query();
        data.put("total", limitRules.size());
        data.put("records", limitRules);
        data.put("current", 1);
        data.put("size", 20);
        data.put("pages", 1);
        return Result.success(data);
    }

    @PostMapping
    public Result<Void> add(@RequestBody LimitRule rule) {
        String uuid = IdUtil.fastSimpleUUID();
        rule.setId(uuid);
        limitHelper.saveOrUpdate(rule);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> edit(@PathVariable String id, @RequestBody LimitRule rule) {
        limitHelper.saveOrUpdate(rule);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        limitHelper.delete(id);
        return Result.success();
    }

}
