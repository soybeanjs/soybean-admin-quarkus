package com.soybean.framework.db.mybatis.auth.permission.service;

import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;

/**
 * @author wenxina
 */
public interface DataScopeService {

    /**
     * 根据用户编号获取数据权限
     *
     * @param userId 用户ID
     * @return 查询结果
     */
    DataScope getDataScopeById(Long userId);
}
