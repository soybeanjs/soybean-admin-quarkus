package com.soybean.uaa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.commons.exception.CheckedException;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.entity.baseinfo.OAuthClientDetails;
import com.soybean.uaa.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 应用管理
 *
 * @author wenxina
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {


    private final ApplicationService applicationService;

    /**
     * 查询应用List
     *
     * @param current    当前
     * @param size       大小
     * @param clientId   客户机id
     * @param clientName 客户端名称
     * @return {@link IPage}<{@link OAuthClientDetails}>
     */
    @GetMapping
    public IPage<OAuthClientDetails> query(@RequestParam(required = false, defaultValue = "1") Integer current,
                                           @RequestParam(required = false, defaultValue = "20") Integer size,
                                           String clientId, String clientName) {
        return this.applicationService.page(new Page<>(current, size),
                Wraps.<OAuthClientDetails>lbQ().like(OAuthClientDetails::getClientId, clientId)
                        .like(OAuthClientDetails::getClientName, clientName));
    }

    /**
     * 添加应用
     *
     * @param dto dto
     */
    @PostMapping
    @SysLog(value = "添加应用")
    public void save(@Validated @RequestBody OAuthClientDetails dto) {
        final long count = this.applicationService.count(Wraps.<OAuthClientDetails>lbQ().eq(OAuthClientDetails::getClientId, dto.getClientId()));
        if (count > 0) {
            throw CheckedException.badRequest("客户ID已存在");
        }
        this.applicationService.save(dto);
    }

    /**
     * 修改应用
     *
     * @param id  id
     * @param dto dto
     */
    @PutMapping("/{id}")
    @SysLog(value = "修改应用")
    public void edit(@PathVariable String id, @Validated @RequestBody OAuthClientDetails dto) {
        this.applicationService.updateById(dto);
    }

    /**
     * 修改应用
     *
     * @param id     id
     * @param status 状态
     */
    @PutMapping("/{id}/{status}")
    @SysLog(value = "修改应用")
    public void status(@PathVariable String id, @PathVariable Boolean status) {
        this.applicationService.updateById(OAuthClientDetails.builder().clientId(id).status(status).build());
    }

    /**
     * 删除应用
     *
     * @param id id
     */
    @DeleteMapping("{id}")
    @SysLog(value = "删除应用")
    public void del(@PathVariable String id) {
        this.applicationService.removeById(id);
    }

}
