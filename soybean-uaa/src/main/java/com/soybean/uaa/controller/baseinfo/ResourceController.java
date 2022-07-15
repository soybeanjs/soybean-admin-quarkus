package com.soybean.uaa.controller.baseinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.commons.util.BeanUtilPlus;
import com.soybean.framework.db.TenantEnvironment;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.mybatis.conditions.query.LbqWrapper;
import com.soybean.uaa.domain.dto.ResourceQueryDTO;
import com.soybean.uaa.domain.dto.ResourceSaveDTO;
import com.soybean.uaa.domain.entity.baseinfo.Resource;
import com.soybean.uaa.domain.enums.ResourceType;
import com.soybean.uaa.domain.vo.VueRouter;
import com.soybean.uaa.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.soybean.uaa.domain.converts.MenuConverts.VUE_ROUTER_2_TREE_NODE_CONVERTS;
import static java.util.stream.Collectors.toList;

/**
 * 菜单资源
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;
    private final TenantEnvironment tenantEnvironment;

    /**
     * 路由菜单
     *
     * @param all 所有
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @GetMapping("/router")
    public Result<List<Tree<Long>>> router(@RequestParam(required = false, defaultValue = "false") Boolean all) {
        List<VueRouter> routers = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(tenantEnvironment.userId()).build());
        List<TreeNode<Long>> list = routers.stream()
                .filter(router -> all || (router.getType() != null && router.getType() == 1 || router.getType() == 5))
                .map(VUE_ROUTER_2_TREE_NODE_CONVERTS::convert).collect(toList());
        return Result.success(TreeUtil.build(list, 0L));
    }

    /**
     * 用户权限
     *
     * @return {@link Result}<{@link List}<{@link String}>>
     */
    @GetMapping("/permissions")
    public Result<List<String>> permissions() {
        List<VueRouter> routers = Optional.ofNullable(resourceService.findVisibleResource(ResourceQueryDTO.builder()
                .userId(tenantEnvironment.userId()).build())).orElseGet(Lists::newArrayList);
        return Result.success(routers.stream().map(VueRouter::getPermission).collect(toList()));
    }

    /**
     * 查询资源
     *
     * @param current  当前
     * @param size     大小
     * @param parentId 父id
     * @param type     类型
     * @return {@link IPage}<{@link Resource}>
     */
    @GetMapping
    public IPage<Resource> query(@RequestParam(required = false, defaultValue = "1") Integer current,
                                 @RequestParam(required = false, defaultValue = "20") Integer size,
                                 Long parentId, Integer type) {
        final LbqWrapper<Resource> wrapper = Wraps.<Resource>lbQ().eq(Resource::getParentId, parentId).eq(Resource::getType, ResourceType.BUTTON);
        return resourceService.page(new Page<>(current, size), wrapper);
    }

    /**
     * 添加资源
     *
     * @param data 数据
     */
    @PostMapping
    @SysLog(value = "添加资源")
    public void save(@Validated @RequestBody ResourceSaveDTO data) {
        Resource resource = BeanUtil.toBean(data, Resource.class);
        resourceService.addResource(resource);
    }


    /**
     * 删除资源
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除资源")
    public void del(@PathVariable Long id) {
        this.resourceService.delResource(id);
    }

    /**
     * 修改资源
     *
     * @param id   id
     * @param data 数据
     */
    @PutMapping("/{id}")
    @SysLog(value = "修改资源")
    public void edit(@PathVariable Long id, @Validated @RequestBody ResourceSaveDTO data) {
        resourceService.editResourceById(BeanUtilPlus.toBean(id, data, Resource.class));
    }


}
