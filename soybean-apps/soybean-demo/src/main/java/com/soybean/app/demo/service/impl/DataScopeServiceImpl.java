package com.soybean.app.demo.service.impl;

import com.soybean.framework.db.configuration.permission.feign.DataScopeFeignClient;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import com.soybean.framework.db.mybatis.auth.permission.service.DataScopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wenxina
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataScopeServiceImpl implements DataScopeService {

    private final DataScopeFeignClient dataScopeFeignClient;

    @Override
    public DataScope getDataScopeById(Long userId) {
        return dataScopeFeignClient.getDataScopeById(userId);
    }
}
