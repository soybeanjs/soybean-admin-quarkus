package com.soybean.uaa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import com.soybean.framework.db.mybatis.conditions.query.LbqWrapper;
import com.soybean.uaa.domain.dto.UserSaveDTO;
import com.soybean.uaa.domain.entity.baseinfo.User;
import com.soybean.uaa.domain.vo.UserResp;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 账号
 * </p>
 *
 * @author wenxina
 * @since 2019-07-03
 */
public interface UserService extends SuperService<User> {


    /**
     * 添加用户
     *
     * @param dto 用户信息
     */
    void addUser(UserSaveDTO dto);

    /**
     * 根据条件查询
     *
     * @param scope scope
     * @return 查询结果
     */
    List<User> list(DataScope scope);

    /**
     * 数据权限 分页
     *
     * @param page    page
     * @param wrapper wrapper
     * @return 查询结果
     */
    IPage<UserResp> findPage(IPage<User> page, LbqWrapper<User> wrapper);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param orgPassword 原始密码
     * @param newPassword 新密码
     */
    void changePassword(Long userId, String orgPassword, String newPassword);

    /**
     * 根据ID删除用户
     *
     * @param id id
     */
    void deleteById(Long id);
}
