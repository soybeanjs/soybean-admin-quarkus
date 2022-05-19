package com.soybean.uaa.repository;

import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.log.LoginLog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 系统日志
 * </p>
 *
 * @author wenxina
 * @since 2019-10-20
 */
@TenantDS
@Repository
public interface LoginLogMapper extends SuperMapper<LoginLog> {


    /**
     * 统计 IP 数据
     *
     * @return 统计结果
     */
    @Select("SELECT count(DISTINCT ( ip )) FROM common_login_log")
    long countDistinctLoginIp();

}
