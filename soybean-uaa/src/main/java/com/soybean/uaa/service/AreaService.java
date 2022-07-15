package com.soybean.uaa.service;

import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.common.AreaEntity;

import java.util.List;

/**
 * @author wenxina
 */
public interface AreaService extends SuperService<AreaEntity> {

    /**
     * 根据 parentId 查询数据集
     *
     * @param parentId parentId
     * @return 查询结果
     */
    List<AreaEntity> listArea(Integer parentId);

    /**
     * 保存或者修改地区
     *
     * @param area area
     */
    void saveOrUpdateArea(AreaEntity area);

}
