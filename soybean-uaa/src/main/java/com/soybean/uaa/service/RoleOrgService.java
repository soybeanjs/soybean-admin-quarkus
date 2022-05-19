package com.soybean.uaa.service;

import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.baseinfo.RoleOrg;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 角色组织关系
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
public interface RoleOrgService extends SuperService<RoleOrg> {

    /**
     * 根据角色id查询
     *
     * @param id
     * @return
     */
    List<Long> listOrgByRoleId(Long id);
}
