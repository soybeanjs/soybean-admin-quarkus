package com.soybean.uaa.service.impl;

import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.entity.common.AreaEntity;
import com.soybean.uaa.repository.AreaMapper;
import com.soybean.uaa.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenxina
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl extends SuperServiceImpl<AreaMapper, AreaEntity> implements AreaService {

    @Override
    public List<AreaEntity> listArea(Integer parentId) {
        return baseMapper.listArea(parentId);
    }

    @Override
    public void saveOrUpdateArea(AreaEntity area) {
        final long count = count(Wraps.<AreaEntity>lbQ().eq(AreaEntity::getId, area.getId()));
        if (count == 0) {
            baseMapper.insert(area);
        } else {
            baseMapper.updateById(area);
        }
    }


}
