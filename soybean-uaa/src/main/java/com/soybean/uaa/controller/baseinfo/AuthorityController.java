package com.soybean.uaa.controller.baseinfo;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScope;
import com.soybean.framework.db.mybatis.auth.permission.service.DataScopeService;
import com.soybean.uaa.domain.entity.baseinfo.Role;
import com.soybean.uaa.domain.entity.baseinfo.User;
import com.soybean.uaa.domain.vo.DictResp;
import com.soybean.uaa.service.RoleService;
import com.soybean.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 用户管理
 *
 * @author wenxina
 * @date 2022/07/12
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthorityController {

    private static final int RECEIVER_TYPE_1 = 1;
    private static final int RECEIVER_TYPE_2 = 2;
    private final UserService userService;
    private final RoleService roleService;
    private final DataScopeService dataScopeService;

    /**
     * [用户]/[角色]-列表
     *
     * @param receiverType [用户:1]/[角色:2]类型
     * @return {@link Result}<{@link List}<{@link DictResp}>>
     */
    @GetMapping("/list/{receiver_type:[12]}/users_or_roles")
    public Result<List<DictResp>> list(@PathVariable("receiver_type") Integer receiverType) {
        List<DictResp> result = Lists.newArrayList();
        // 查询角色
        if (receiverType == RECEIVER_TYPE_1) {
            final List<User> users = this.userService.list(new DataScope());
            if (CollectionUtil.isNotEmpty(users)) {
                result = users.stream().map(user -> DictResp.builder()
                        .label(user.getNickName()).value(user.getId()).build()).collect(toList());
            }
        } else if (receiverType == RECEIVER_TYPE_2) {
            final List<Role> roles = this.roleService.list(new DataScope());
            if (CollectionUtil.isNotEmpty(roles)) {
                result = roles.stream().map(role -> DictResp.builder()
                        .label(role.getName()).value(role.getId()).build()).collect(toList());
            }
        }
        return Result.success(result);
    }

    /**
     * 通过id获取数据范围
     *
     * @param userId 用户id
     * @return {@link DataScope}
     */
    @GetMapping("/getDataScopeById")
    public DataScope getDataScopeById(Long userId) {
        return dataScopeService.getDataScopeById(userId);
    }

}
