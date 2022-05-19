package com.soybean.gateway.controller;

import com.soybean.framework.commons.entity.Result;
import com.soybean.gateway.config.rule.BlacklistHelper;
import com.soybean.gateway.controller.domain.BlacklistRule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 黑名单控制器
 *
 * @author wenxina
 * @date 2022/03/22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gateway/rules/blacklist")
public class BlacklistController {

    private final BlacklistHelper blacklistHelper;

    @GetMapping
    public Result<List<BlacklistRule>> query() {
        return Result.success(blacklistHelper.query());
    }

    @PostMapping
    public Result<Void> saveOrUpdate(@RequestBody BlacklistRule rule) {
        blacklistHelper.saveOrUpdate(rule);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        blacklistHelper.delete(id);
        return Result.success();
    }

}
