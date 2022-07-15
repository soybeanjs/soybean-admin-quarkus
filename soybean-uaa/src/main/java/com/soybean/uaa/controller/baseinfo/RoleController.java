package com.soybean.uaa.controller.baseinfo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.commons.util.BeanUtilPlus;
import com.soybean.framework.db.TenantEnvironment;
import com.soybean.framework.db.mybatis.auth.permission.prop.DataScopeType;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.dto.RoleDTO;
import com.soybean.uaa.domain.dto.RoleResSaveDTO;
import com.soybean.uaa.domain.dto.RoleUserDTO;
import com.soybean.uaa.domain.entity.baseinfo.Role;
import com.soybean.uaa.domain.entity.baseinfo.RoleOrg;
import com.soybean.uaa.domain.entity.baseinfo.RoleRes;
import com.soybean.uaa.domain.vo.RoleDetailVO;
import com.soybean.uaa.domain.vo.RolePermissionResp;
import com.soybean.uaa.domain.vo.RoleResVO;
import com.soybean.uaa.domain.vo.UserRoleResp;
import com.soybean.uaa.service.RoleOrgService;
import com.soybean.uaa.service.RoleResService;
import com.soybean.uaa.service.RoleService;
import com.soybean.uaa.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final TenantEnvironment tenantEnvironment;
    private final RoleService roleService;
    private final RoleResService roleResService;
    private final RoleOrgService roleOrgService;
    private final UserRoleService userRoleService;


    /**
     * 查询全部角色
     *
     * @return {@link Result}<{@link List}<{@link Role}>>
     */
    @GetMapping("/query_all")
    public Result<List<Role>> query() {
        final List<Role> page = this.roleService.list();
        return Result.success(page);
    }

    /**
     * 查询角色
     *
     * @param current   当前
     * @param size      大小
     * @param name      名字
     * @param locked    锁着
     * @param scopeType 范围类型
     * @return {@link IPage}<{@link Role}>
     */
    @GetMapping
    public IPage<Role> query(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer size,
                             String name, Boolean locked, DataScopeType scopeType) {
        return this.roleService.page(new Page<>(current, size), Wraps.<Role>lbQ()
                .eq(Role::getLocked, locked).like(Role::getName, name).eq(Role::getScopeType, scopeType));
    }

    /**
     * 角色详情
     *
     * @param id id
     * @return {@link RoleDetailVO}
     */
    @GetMapping("/{id}/detail")
    public RoleDetailVO details(@PathVariable Long id) {
        Role role = roleService.getById(id);
        RoleDetailVO detail = BeanUtilPlus.toBean(role, RoleDetailVO.class);
        final RoleResVO authority = this.roleResService.findAuthorityIdByRoleId(id);
        detail.setAuthority(authority);
        return detail;
    }

    /**
     * 添加角色
     *
     * @param data 数据
     */
    @PostMapping
    @SysLog(value = "添加角色")
    public void add(@Validated @RequestBody RoleDTO data) {
        roleService.saveRole(tenantEnvironment.userId(), data);
    }

    /**
     * 编辑角色
     *
     * @param id   id
     * @param data 数据
     */
    @PutMapping("/{id}")
    @SysLog(value = "编辑角色")
    public void edit(@PathVariable Long id, @Validated @RequestBody RoleDTO data) {
        roleService.updateRole(id, tenantEnvironment.userId(), data);
    }

    /**
     * 删除角色
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除角色")
    public void del(@PathVariable Long id) {
        this.roleService.removeByRoleId(id);
    }

    /**
     * 通过角色id查询用户
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link UserRoleResp}>
     */
    @GetMapping("/{roleId}/users")
    public Result<UserRoleResp> userByRoleId(@PathVariable Long roleId) {
        return Result.success(userRoleService.findUserByRoleId(roleId));
    }

    /**
     * 通过角色id查询权限
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link RolePermissionResp}>
     */
    @GetMapping("/{role_id}/resources/permissions")
    public Result<RolePermissionResp> permission(@PathVariable("role_id") Long roleId) {
        return Result.success(this.roleService.findRolePermissionById(roleId));
    }

    /**
     * 通过角色id查询资源
     *
     * @param roleId 角色id
     * @return {@link List}<{@link RoleRes}>
     */
    @GetMapping("/{roleId}/role_res")
    public List<RoleRes> resByRoleId(@PathVariable Long roleId) {
        return roleResService.list(Wraps.<RoleRes>lbQ().eq(RoleRes::getRoleId, roleId));
    }

    /**
     * 通过角色id查询组织
     *
     * @param roleId 角色id
     * @return {@link List}<{@link RoleOrg}>
     */
    @GetMapping("/{roleId}/role_rog")
    public List<RoleOrg> orgByRoleId(@PathVariable Long roleId) {
        return roleOrgService.list(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, roleId));
    }

    /**
     * 分配权限
     *
     * @param roleId 角色id
     * @param dto    dto
     */
    @PostMapping("/{roleId}/authority")
    public void distributionAuthority(@PathVariable Long roleId, @RequestBody RoleResSaveDTO dto) {
        this.roleResService.saveRoleAuthority(dto);
    }

    /**
     * 分配用户
     *
     * @param roleId 角色id
     * @param dto    dto
     */
    @PostMapping("/{roleId}/users")
    public void distributionUser(@PathVariable Long roleId, @RequestBody RoleUserDTO dto) {
        this.roleService.saveUserRole(roleId, dto.getUserIdList());
    }


}
