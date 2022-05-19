package com.soybean.uaa.service;

import com.soybean.framework.db.configuration.dynamic.event.body.EventAction;
import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.tenant.DynamicDatasource;
import com.soybean.uaa.domain.vo.TenantDynamicDatasourceVO;

import java.util.List;

/**
 * @author wenxina
 */
public interface DynamicDatasourceService extends SuperService<DynamicDatasource> {

    /**
     * 查询所有可用的动态数据源
     *
     * @return 查询结果
     */
    List<TenantDynamicDatasourceVO> selectTenantDynamicDatasource();

    void ping(Long id);

    void saveOrUpdateDatabase(DynamicDatasource dynamicDatasource);

    void removeDatabaseById(Long id);

    void publishEvent(EventAction action, Long tenantId);
}
