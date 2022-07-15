package com.soybean.uaa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.dto.StationPageDTO;
import com.soybean.uaa.domain.entity.baseinfo.Station;

/**
 * <p>
 * 业务接口
 * 岗位
 * </p>
 *
 * @author wenxina
 * @since 2022-07-01
 */
public interface StationService extends SuperService<Station> {
    /**
     * 按权限查询岗位的分页信息
     *
     * @param params params
     * @param data   data
     * @return Station
     */
    IPage<Station> findStationPage(PageRequest params, StationPageDTO data);
}
