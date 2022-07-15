package com.soybean.uaa.controller.baseinfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.dto.UserSaveDTO;
import com.soybean.uaa.domain.dto.UserUpdateDTO;
import com.soybean.uaa.domain.entity.baseinfo.User;
import com.soybean.uaa.domain.enums.Sex;
import com.soybean.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.soybean.uaa.domain.converts.UserConverts.USER_DTO_2_PO_CONVERTS;


/**
 * 用户管理
 *
 * @author wenxina
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 查询用户
     *
     * @param current  当前
     * @param size     大小
     * @param username 用户名
     * @param nickName 尼克名字
     * @param sex      性
     * @param email    电子邮件
     * @param orgId    org id
     * @param mobile   移动
     * @return {@link IPage}<{@link User}>
     */
    @GetMapping
    public IPage<User> query(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer size,
                             String username, String nickName, Integer sex, String email, Long orgId, String mobile) {
        return this.userService.page(new Page<>(current, size),
                Wraps.<User>lbQ().eq(User::getSex, Sex.of(sex)).eq(User::getOrgId, orgId)
                        .like(User::getNickName, nickName).like(User::getMobile, mobile)
                        .like(User::getUsername, username).like(User::getEmail, email));
    }


    /**
     * 添加用户
     *
     * @param dto dto
     */
    @PostMapping
    @SysLog(value = "添加用户")
    public void save(@Validated @RequestBody UserSaveDTO dto) {
        this.userService.addUser(dto);
    }


    /**
     * 编辑用户
     *
     * @param id  id
     * @param dto dto
     */
    @PutMapping("{id}")
    @SysLog(value = "编辑用户")
    public void edit(@PathVariable Long id, @Validated @RequestBody UserUpdateDTO dto) {
        this.userService.updateById(USER_DTO_2_PO_CONVERTS.convert(dto, id));
    }


    /**
     * 删除用户
     *
     * @param id id
     */
    @DeleteMapping("{id}")
    @SysLog(value = "删除用户")
    public void del(@PathVariable Long id) {
        this.userService.deleteById(id);
    }
}
