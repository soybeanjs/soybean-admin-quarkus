package com.soybean.uaa.repository;

import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.baseinfo.RoleOrg;
import org.springframework.stereotype.Repository;

/**
 * @author wenxina
 */
@TenantDS
@Repository
public interface RoleOrgMapper extends SuperMapper<RoleOrg> {

}
