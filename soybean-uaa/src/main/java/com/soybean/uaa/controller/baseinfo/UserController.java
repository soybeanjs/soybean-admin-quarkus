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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户管理", description = "用户管理")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Parameters({
            @Parameter(description = "账号", name = "username", in = ParameterIn.QUERY),
            @Parameter(description = "名称", name = "nickName", in = ParameterIn.QUERY),
            @Parameter(description = "邮箱", name = "email", in = ParameterIn.QUERY),
            @Parameter(description = "性别", name = "sex", in = ParameterIn.QUERY),
            @Parameter(description = "手机号", name = "mobile", in = ParameterIn.QUERY),
            @Parameter(description = "组织", name = "orgId", in = ParameterIn.QUERY)
    })
    @Operation(summary = "用户列表 - [wenxina] - [DONE]")
    public IPage<User> query(@Parameter(description = "当前页") @RequestParam(required = false, defaultValue = "1") Integer current,
                             @Parameter(description = "条数") @RequestParam(required = false, defaultValue = "20") Integer size,
                             String username, String nickName, Integer sex, String email, Long orgId, String mobile) {
        return this.userService.page(new Page<>(current, size),
                Wraps.<User>lbQ().eq(User::getSex, Sex.of(sex)).eq(User::getOrgId, orgId)
                        .like(User::getNickName, nickName).like(User::getMobile, mobile)
                        .like(User::getUsername, username).like(User::getEmail, email));
    }


    @PostMapping
    @SysLog(value = "添加用户")
    @Operation(summary = "添加用户")
    public void save(@Validated @RequestBody UserSaveDTO dto) {
        this.userService.addUser(dto);
    }


    @PutMapping("{id}")
    @SysLog(value = "编辑用户")
    @Operation(summary = "编辑用户")
    public void edit(@PathVariable Long id, @Validated @RequestBody UserUpdateDTO dto) {
        this.userService.updateById(USER_DTO_2_PO_CONVERTS.convert(dto, id));
    }


    @DeleteMapping("{id}")
    @SysLog(value = "删除用户")
    @Operation(summary = "删除用户")
    public void del(@PathVariable Long id) {
        this.userService.deleteById(id);
    }
}
