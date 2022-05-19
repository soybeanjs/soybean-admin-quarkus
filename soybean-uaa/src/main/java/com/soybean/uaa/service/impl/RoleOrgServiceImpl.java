package com.soybean.uaa.service.impl;

import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.entity.baseinfo.RoleOrg;
import com.soybean.uaa.repository.RoleOrgMapper;
import com.soybean.uaa.service.RoleOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 角色组织关系
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
@Slf4j
@Service
public class RoleOrgServiceImpl extends SuperServiceImpl<RoleOrgMapper, RoleOrg> implements RoleOrgService {
    @Override
    public List<Long> listOrgByRoleId(Long id) {
        List<RoleOrg> list = super.list(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, id));
        List<Long> orgList = list.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
        return orgList;
    }
}
