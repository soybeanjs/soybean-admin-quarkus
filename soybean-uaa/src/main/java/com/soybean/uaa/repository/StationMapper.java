package com.soybean.uaa.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import com.soybean.uaa.domain.entity.baseinfo.Station;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 岗位
 * </p>
 *
 * @author wenxina
 * @since 2022-07-01
 */
@TenantDS
@Repository
public interface StationMapper extends SuperMapper<Station> {
    /**
     * 分页查询岗位信息（含角色）
     *
     * @param page
     * @param queryWrapper
     * @param dataScope
     * @return
     */
    IPage<Station> findStationPage(IPage page, @Param(Constants.WRAPPER) Wrapper<Station> queryWrapper, DataScope dataScope);
}
