package com.soybean.uaa.repository;

import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.common.AreaEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wenxina
 */
@TenantDS
@Repository
public interface AreaMapper extends SuperMapper<AreaEntity> {

    /**
     * 根据 parentId 查询数据集
     *
     * @param parentId parentId
     * @return 查询结果
     */
    List<AreaEntity> listArea(@Param("parentId") Integer parentId);
}
