package com.soybean.uaa.service;

import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.baseinfo.UserRole;
import com.soybean.uaa.domain.vo.UserRoleResp;

/**
 * <p>
 * 业务接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
public interface UserRoleService extends SuperService<UserRole> {

    /**
     * 根据劫色查询用户
     *
     * @param roleId 角色id
     * @return 查询结果
     */
    UserRoleResp findUserByRoleId(Long roleId);

}
