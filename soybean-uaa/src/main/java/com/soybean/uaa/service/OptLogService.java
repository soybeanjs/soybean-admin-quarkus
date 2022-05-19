package com.soybean.uaa.service;

import com.soybean.framework.boot.log.OptLogDTO;
import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.log.OptLog;

/**
 * @author wenxina
 */
public interface OptLogService extends SuperService<OptLog> {

    /**
     * 保存操作日志
     *
     * @param dto dto
     */
    void save(OptLogDTO dto);
}
