package com.soybean.uaa.repository;

import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.tenant.TenantConfig;
import com.soybean.uaa.domain.vo.TenantDynamicDatasourceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wenxina
 */
@Repository
public interface TenantConfigMapper extends SuperMapper<TenantConfig> {

    /**
     * 查询所有可用的动态数据源
     *
     * @param datasourceId datasourceId
     * @return 查询结果
     */
    List<TenantDynamicDatasourceVO> selectTenantDynamicDatasource(@Param("datasourceId") Long datasourceId);

    /**
     * 获取租户动态数据源
     *
     * @param tenantId tenantId
     * @return 查询结果
     */
    TenantDynamicDatasourceVO getTenantDynamicDatasourceByTenantId(Long tenantId);

}
