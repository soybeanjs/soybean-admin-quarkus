package com.soybean.uaa.repository;

import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.baseinfo.UserRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
@TenantDS
@Repository
public interface UserRoleMapper extends SuperMapper<UserRole> {


}
