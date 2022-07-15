package com.soybean.uaa.controller.common;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.google.common.collect.Maps;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.dto.AreaEntityDTO;
import com.soybean.uaa.domain.entity.common.AreaEntity;
import com.soybean.uaa.domain.vo.AreaNodeResp;
import com.soybean.uaa.service.AreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.soybean.uaa.domain.converts.AreaConverts.AREA_DTO_2_PO_CONVERTS;
import static com.soybean.uaa.domain.converts.AreaConverts.AREA_ENTITY_2_NODE_RESP_CONVERTS;

/**
 * <p>
 * 地区管理
 * </p>
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    /**
     * 查询系统所有的地区树
     *
     * @return {@link Result}<{@link List}<{@link Tree}<{@link Long}>>>
     */
    @GetMapping("/trees")
    public Result<List<Tree<Long>>> tree() {
        List<AreaEntity> list = this.areaService.list(Wraps.<AreaEntity>lbQ().orderByAsc(AreaEntity::getSequence));
        final List<TreeNode<Long>> nodes = list.stream().map(area -> {
            TreeNode<Long> treeNode = new TreeNode<>(area.getId(), area.getParentId(), area.getName(), area.getSequence());
            Map<String, Object> extra = Maps.newLinkedHashMap();
            extra.put("key", area.getId());
            extra.put("value", area.getId());
            extra.put("label", area.getName());
            extra.put("level", area.getLevel());
            extra.put("longitude", area.getLongitude());
            extra.put("latitude", area.getLatitude());
            treeNode.setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        return Result.success(TreeUtil.build(nodes, 0L));
    }

    /**
     * 地区列表
     *
     * @param parentId 父id
     * @return {@link Result}<{@link List}<{@link AreaNodeResp}>>
     */
    @GetMapping("/{parent_id}/children")
    public Result<List<AreaNodeResp>> list(@PathVariable(name = "parent_id") Integer parentId) {
        final List<AreaEntity> list = this.areaService.listArea(parentId);
        return Result.success(AREA_ENTITY_2_NODE_RESP_CONVERTS.converts(list));
    }

    /**
     * 保存地区
     *
     * @param dto dto
     */
    @PostMapping
    public void save(@Validated @RequestBody AreaEntityDTO dto) {
        this.areaService.saveOrUpdateArea(AREA_DTO_2_PO_CONVERTS.convert(dto));
    }

    /**
     * 批量删除地区
     *
     * @param ids id
     */
    @DeleteMapping
    public void batchDel(@RequestBody List<Long> ids) {
        this.areaService.removeByIds(ids);
    }

    /**
     * 删除地区
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        this.areaService.removeById(id);
    }
}
