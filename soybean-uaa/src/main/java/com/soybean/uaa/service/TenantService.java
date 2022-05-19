package com.soybean.uaa.service;

import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.tenant.Tenant;
import com.soybean.uaa.domain.entity.tenant.TenantConfig;

/**
 * @author wenxina
 */
public interface TenantService extends SuperService<Tenant> {


    /**
     * 保存租户
     *
     * @param tenant 租户信息
     */
    void saveOrUpdateTenant(Tenant tenant);

    void tenantConfig(TenantConfig tenantConfig);

    void initSqlScript(Long id);
}
