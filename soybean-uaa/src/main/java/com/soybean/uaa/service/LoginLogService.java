package com.soybean.uaa.service;


import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.log.LoginLog;

/**
 * <p>
 * 业务接口
 * 系统日志
 *
 * </p>
 *
 * @author wenxina
 * @since 2019-10-20
 */
public interface LoginLogService extends SuperService<LoginLog> {

    /**
     * 记录登录日志
     *
     * @param userId    用户ID
     * @param principal 账号
     * @param realName  名字
     * @return LoginLog
     */
    LoginLog saveLoginLog(Long userId, String principal, String realName);

}
