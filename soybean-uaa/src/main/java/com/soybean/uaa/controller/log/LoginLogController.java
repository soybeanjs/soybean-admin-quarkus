package com.soybean.uaa.controller.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.entity.log.LoginLog;
import com.soybean.uaa.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login_logs")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    /**
     * 查询登录日志
     *
     * @param request   请求
     * @param name      名字
     * @param principal 主要
     * @return {@link Result}<{@link Page}<{@link LoginLog}>>
     */
    @GetMapping
    public Result<Page<LoginLog>> query(PageRequest request, String name, String principal) {
        final Page<LoginLog> page = this.loginLogService.page(request.buildPage(), Wraps.<LoginLog>lbQ()
                .like(LoginLog::getName, name)
                .like(LoginLog::getPrincipal, principal).orderByDesc(LoginLog::getCreatedTime));
        return Result.success(page);
    }


}
