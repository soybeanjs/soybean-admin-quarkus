package com.soybean.uaa.controller.tenant;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.commons.util.BeanUtilPlus;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.security.client.annotation.IgnoreAuthorize;
import com.soybean.uaa.domain.dto.TenantConfigDTO;
import com.soybean.uaa.domain.dto.TenantPageDTO;
import com.soybean.uaa.domain.dto.TenantSaveDTO;
import com.soybean.uaa.domain.entity.tenant.Tenant;
import com.soybean.uaa.domain.entity.tenant.TenantConfig;
import com.soybean.uaa.domain.vo.TenantDynamicDatasourceVO;
import com.soybean.uaa.service.DynamicDatasourceService;
import com.soybean.uaa.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户管理
 *
 * @author wenxina
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenants")
@Tag(name = "租户管理", description = "租户管理")
public class TenantController {

    private final TenantService tenantService;
    private final DynamicDatasourceService dynamicDatasourceService;

    @GetMapping
    @Operation(summary = "租户列表 - [wenxina] - [DONE]")
    public IPage<Tenant> query(TenantPageDTO params) {
        return tenantService.page(params.buildPage(), Wraps.<Tenant>lbQ()
                .like(Tenant::getName, params.getName()).eq(Tenant::getCode, params.getCode())
                .eq(Tenant::getProvinceId, params.getProvinceId())
                .eq(Tenant::getCityId, params.getCityId())
                .eq(Tenant::getDistrictId, params.getDistrictId())
                .eq(Tenant::getIndustry, params.getIndustry()).eq(Tenant::getStatus, params.getStatus())
                .eq(Tenant::getType, params.getType()));
    }

    @IgnoreAuthorize
    @Operation(summary = "查询可用", description = "查询可用数据源")
    @GetMapping("/databases/active")
    public List<TenantDynamicDatasourceVO> queryActive() {
        return this.dynamicDatasourceService.selectTenantDynamicDatasource();
    }


    @PostMapping
    @SysLog(value = "添加租户")
    @Operation(summary = "添加租户")
    public void add(@Validated @RequestBody TenantSaveDTO dto) {
        tenantService.saveOrUpdateTenant(BeanUtil.toBean(dto, Tenant.class));
    }

    @PutMapping("/{id}")
    @SysLog(value = "编辑租户")
    @Operation(summary = "编辑租户")
    public void edit(@PathVariable Long id, @Validated @RequestBody TenantSaveDTO dto) {
        tenantService.saveOrUpdateTenant(BeanUtilPlus.toBean(id, dto, Tenant.class));
    }

    @PutMapping("/{id}/config")
    @SysLog(value = "配置租户")
    @Operation(summary = "配置租户")
    public void config(@PathVariable Long id, @Validated @RequestBody TenantConfigDTO dto) {
        tenantService.tenantConfig(TenantConfig.builder().tenantId(id).dynamicDatasourceId(dto.getDynamicDatasourceId()).build());
    }

    @PutMapping("/{id}/init_sql_script")
    @SysLog(value = "加载初始数据")
    @Operation(summary = "加载初始数据")
    public void initSqlScript(@PathVariable Long id) {
        tenantService.initSqlScript(id);
    }

    @DeleteMapping("/{id}")
    @SysLog(value = "删除租户")
    @Operation(summary = "删除租户")
    public void del(@PathVariable Long id) {
        tenantService.removeById(id);
    }

}
