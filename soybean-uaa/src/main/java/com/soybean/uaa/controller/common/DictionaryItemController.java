package com.soybean.uaa.controller.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.framework.commons.entity.Result;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.dto.DictionaryItemDTO;
import com.soybean.uaa.domain.entity.common.DictionaryItem;
import com.soybean.uaa.service.DictionaryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.soybean.uaa.domain.converts.DictionaryConverts.DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS;

/**
 * 字典项
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionaries/{dictionary_id}/items")
public class DictionaryItemController {

    private final DictionaryItemService dictionaryItemService;

    /**
     * 查询字典项
     *
     * @param dictionaryId 字典id
     * @param label        标签
     * @param status       状态
     * @param params       参数个数
     * @return {@link Result}<{@link Page}<{@link DictionaryItem}>>
     */
    @GetMapping
    public Result<Page<DictionaryItem>> query(@PathVariable("dictionary_id") Long dictionaryId, String label, Boolean status, PageRequest params) {
        final Page<DictionaryItem> itemPage = this.dictionaryItemService.page(params.buildPage(), Wraps.<DictionaryItem>lbQ()
                .like(DictionaryItem::getLabel, label).eq(DictionaryItem::getStatus, status)
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        return Result.success(itemPage);
    }

    /**
     * 保存字典项
     *
     * @param dictionaryId 字典id
     * @param dto          dto
     */
    @PostMapping
    public void save(@PathVariable("dictionary_id") Long dictionaryId, @Validated @RequestBody DictionaryItemDTO dto) {
        this.dictionaryItemService.addDictionaryItem(dictionaryId, DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS.convert(dto));
    }

    /**
     * 编辑字典项
     *
     * @param dictionaryId 字典id
     * @param id           id
     * @param dto          dto
     */
    @PutMapping("/{id}")
    public void edit(@PathVariable("dictionary_id") Long dictionaryId, @PathVariable Long id, @Validated @RequestBody DictionaryItemDTO dto) {
        final DictionaryItem dictionaryItem = DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS.convert(dto);
        dictionaryItem.setId(id);
        this.dictionaryItemService.editDictionaryItem(dictionaryId, dictionaryItem);
    }

    /**
     * 删除字典项
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id) {
        this.dictionaryItemService.removeById(id);
    }

}
