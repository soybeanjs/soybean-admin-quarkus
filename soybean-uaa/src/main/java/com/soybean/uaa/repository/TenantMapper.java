package com.soybean.uaa.repository;

import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.tenant.Tenant;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wenxina
 */
@Repository
public interface TenantMapper extends SuperMapper<Tenant> {

    /**
     * 测试数据源获取数据
     *
     * @return 查询结果
     */
    @Select("select * from t_tenant")
    List<Tenant> queryDbTestTenant();
}
