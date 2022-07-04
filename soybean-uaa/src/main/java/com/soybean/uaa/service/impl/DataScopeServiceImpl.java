package com.soybean.uaa.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScopeType;
import com.soybean.framework.db.mybatis.auth.permission.service.DataScopeService;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.entity.baseinfo.Org;
import com.soybean.uaa.domain.entity.baseinfo.Role;
import com.soybean.uaa.domain.entity.baseinfo.RoleOrg;
import com.soybean.uaa.domain.entity.baseinfo.User;
import com.soybean.uaa.repository.OrgMapper;
import com.soybean.uaa.repository.RoleMapper;
import com.soybean.uaa.repository.RoleOrgMapper;
import com.soybean.uaa.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wenxina
 */
@Slf4j
@Service
public class DataScopeServiceImpl implements DataScopeService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleOrgMapper roleOrgMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrgMapper orgMapper;

    @Override
    public DataScope getDataScopeById(Long userId) {
        DataScope scope = new DataScope();
        Set<Long> orgIds = Sets.newHashSet();
        List<Role> list = roleMapper.findRoleByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            scope.setSelf(true);
            return scope;
        }
        // 找到 dsType 最大的角色， dsType越大，角色拥有的权限最大
        Optional<Role> max = list.stream().max(Comparator.comparingInt((item) -> item.getScopeType().getVal()));
        if (max.isEmpty()) {
            scope.setSelf(true);
            return scope;
        }
        Role role = max.get();
        DataScopeType scopeType = role.getScopeType();
        scope.setScopeType(role.getScopeType());
        if (DataScopeType.ALL.eq(scopeType)) {
            scope.setAll(true);
        } else if (DataScopeType.CUSTOMIZE.eq(scopeType)) {
            List<RoleOrg> roleOrgList = roleOrgMapper.selectList(Wraps.<RoleOrg>lbQ()
                    .select(RoleOrg::getOrgId)
                    .eq(RoleOrg::getRoleId, role.getId()));
            orgIds.addAll(roleOrgList.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList()));
        } else if (DataScopeType.THIS_LEVEL.eq(scopeType)) {
            User user = userMapper.selectById(userId);
            if (user != null && user.getOrgId() != null) {
                orgIds.add(user.getOrgId());
            }
        } else if (DataScopeType.THIS_LEVEL_CHILDREN.eq(scopeType)) {
            User user = userMapper.selectById(userId);
            if (user != null && user.getOrgId() != null) {
                List<Org> orgList = findChildren(Collections.singletonList(user.getOrgId()));
                if (CollectionUtil.isNotEmpty(orgList)) {
                    orgIds.addAll(orgList.stream().mapToLong(Org::getId).boxed().collect(Collectors.toSet()));
                }
            }
        } else {
            scope.setSelf(true);
        }
        scope.setOrgIds(orgIds);
        return scope;
    }

    public List<Org> findChildren(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // MySQL 全文索引
        String applySql = String.format(" MATCH(tree_path) AGAINST('%s' IN BOOLEAN MODE) ", StringUtils.join(ids, " "));

        return orgMapper.selectList(Wraps.<Org>lbQ().in(Org::getId, ids).or(query -> query.apply(applySql)));
    }


}
