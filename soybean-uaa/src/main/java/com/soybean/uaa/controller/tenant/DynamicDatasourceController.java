package com.soybean.uaa.controller.tenant;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.commons.util.BeanUtilPlus;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.dto.DynamicDatasourceReq;
import com.soybean.uaa.domain.entity.tenant.DynamicDatasource;
import com.soybean.uaa.service.DynamicDatasourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 动态数据源
 *
 * @author wenxina
 * @date 2022/07/12
 */
@Slf4j
@RestController
@RequestMapping("/databases")
@RequiredArgsConstructor
@Validated
public class DynamicDatasourceController {

    private final DynamicDatasourceService dynamicDatasourceService;

    /**
     * 动态数据源List
     *
     * @param pageRequest 页面请求
     * @param database    数据库
     * @return {@link Result}<{@link Page}<{@link DynamicDatasource}>>
     */
    @GetMapping
    public Result<Page<DynamicDatasource>> page(PageRequest pageRequest, String database) {
        final Page<DynamicDatasource> page = dynamicDatasourceService.page(pageRequest.buildPage(),
                Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getDatabase, database));
        return Result.success(page);
    }

    /**
     * 查询当前激活数据源
     *
     * @return {@link Result}<{@link List}<{@link DynamicDatasource}>>
     */
    @GetMapping("/active")
    public Result<List<DynamicDatasource>> queryActive() {
        return Result.success(this.dynamicDatasourceService.list(Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getLocked, false)));
    }

    /**
     * ping数据源
     *
     * @param id id
     */
    @GetMapping("/{id}/ping")
    public void ping(@PathVariable Long id) {
        this.dynamicDatasourceService.ping(id);
    }

    /**
     * 添加数据源
     *
     * @param req req
     */
    @PostMapping
    public void add(@Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtil.toBean(req, DynamicDatasource.class));
    }

    /**
     * 编辑数据源
     *
     * @param id  id
     * @param req req
     */
    @PutMapping("/{id}")
    public void edit(@PathVariable Long id, @Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtilPlus.toBean(id, req, DynamicDatasource.class));
    }

    /**
     * 删除数据源
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        dynamicDatasourceService.removeDatabaseById(id);
    }
}
