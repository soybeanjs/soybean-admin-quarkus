package com.soybean.uaa.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.dto.RoleResSaveDTO;
import com.soybean.uaa.domain.dto.UserRoleSaveDTO;
import com.soybean.uaa.domain.entity.baseinfo.RoleRes;
import com.soybean.uaa.domain.entity.baseinfo.UserRole;
import com.soybean.uaa.domain.vo.RoleResMenuMapperResp;
import com.soybean.uaa.domain.vo.RoleResVO;
import com.soybean.uaa.repository.RoleResMapper;
import com.soybean.uaa.service.RoleResService;
import com.soybean.uaa.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 业务实现类
 * 角色的资源
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleResServiceImpl extends SuperServiceImpl<RoleResMapper, RoleRes> implements RoleResService {

    private final UserRoleService userRoleService;

    @Override
    public RoleResVO findAuthorityIdByRoleId(Long roleId) {
        final List<RoleResMenuMapperResp> list = this.baseMapper.selectRoleResByRoleId(roleId);
        List<Long> menuIdList = list.stream().filter(xx -> xx.getType() == 1 || xx.getType() == 5)
                .mapToLong(RoleResMenuMapperResp::getId).boxed().distinct().collect(Collectors.toList());
        List<Long> resourceIdList = list.stream().filter(xx -> xx.getType() == 2)
                .mapToLong(RoleResMenuMapperResp::getId).boxed().distinct().collect(Collectors.toList());
        return RoleResVO.builder()
                .menuIdList(menuIdList)
                .resourceIdList(resourceIdList)
                .build();
    }


    @Override
    public boolean saveUserRole(UserRoleSaveDTO userRole) {
        userRoleService.remove(Wraps.<UserRole>lbQ().eq(UserRole::getRoleId, userRole.getRoleId()));
        List<UserRole> list = userRole.getUserIdList()
                .stream()
                .map((userId) -> UserRole.builder()
                        .userId(userId)
                        .roleId(userRole.getRoleId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(list);
        return true;
    }

    @Override
    @DSTransactional
    public void saveRoleAuthority(RoleResSaveDTO dto) {
        //删除角色和资源的关联
        super.remove(Wraps.<RoleRes>lbQ().eq(RoleRes::getRoleId, dto.getRoleId()));
        resHandler(dto, dto.getRoleId());
    }

    private void resHandler(RoleResSaveDTO data, Long roleId) {
        final Set<Long> set = data.getResIds();
        if (CollectionUtil.isEmpty(set)) {
            return;
        }
        final List<RoleRes> roleRes = set.stream().filter(Objects::nonNull)
                .map(resId -> RoleRes.builder().resId(resId).roleId(roleId).build())
                .collect(toList());
        super.insertBatch(roleRes);
    }
}
