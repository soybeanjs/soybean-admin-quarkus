package com.soybean.uaa.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.soybean.framework.boot.RegionUtils;
import com.soybean.framework.boot.log.OptLogDTO;
import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.uaa.domain.entity.log.OptLog;
import com.soybean.uaa.repository.OptLogMapper;
import com.soybean.uaa.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wenxina
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OptLogServiceImpl extends SuperServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    private final OptLogMapper optLogMapper;

    @Override
    public void save(OptLogDTO dto) {
        DynamicDataSourceContextHolder.push(dto.getDsKey());
        log.info("[日志信息] - {}", JSON.toJSONString(dto));
        final OptLog record = BeanUtil.toBean(dto, OptLog.class);
        record.setLocation(RegionUtils.getRegion(dto.getIp()));
        this.optLogMapper.insert(record);
        DynamicDataSourceContextHolder.poll();
    }

}
