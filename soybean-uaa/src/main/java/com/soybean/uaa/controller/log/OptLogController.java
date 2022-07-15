package com.soybean.uaa.controller.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.entity.log.OptLog;
import com.soybean.uaa.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/opt_logs")
@TenantDS
@RequiredArgsConstructor
public class OptLogController {


    private final OptLogService optLogService;

    /**
     * 查询操作日志
     *
     * @param request     请求
     * @param location    位置
     * @param description 描述
     * @return {@link Result}<{@link Page}<{@link OptLog}>>
     */
    @GetMapping
    public Result<Page<OptLog>> query(PageRequest request, String location, String description) {
        final Page<OptLog> page = this.optLogService.page(request.buildPage(), Wraps.<OptLog>lbQ()
                .like(OptLog::getLocation, location)
                .like(OptLog::getDescription, description).orderByDesc(OptLog::getStartTime));
        return Result.success(page);
    }

    /**
     * 批量删除操作日志
     *
     * @param day 一天
     */
    @DeleteMapping("/{day}")
    public void batchDelete(@PathVariable Integer day) {
        this.optLogService.remove(Wraps.<OptLog>lbQ().le(OptLog::getStartTime, LocalDateTime.now().plusDays(-day)));
    }


}
