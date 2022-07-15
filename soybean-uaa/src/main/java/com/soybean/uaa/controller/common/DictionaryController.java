package com.soybean.uaa.controller.common;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.framework.db.page.PageRequest;
import com.soybean.uaa.domain.dto.DictionaryDTO;
import com.soybean.uaa.domain.entity.common.Dictionary;
import com.soybean.uaa.domain.entity.common.DictionaryItem;
import com.soybean.uaa.service.DictionaryItemService;
import com.soybean.uaa.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.soybean.uaa.domain.converts.DictionaryConverts.DICTIONARY_DTO_2_PO_CONVERTS;

/**
 * 字典类型
 *
 * @author wenxina
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionaries")
@RequiredArgsConstructor
public class DictionaryController {


    private final DictionaryService dictionaryService;
    private final DictionaryItemService dictionaryItemService;

    /**
     * 字典查询
     *
     * @param pageRequest 页面请求
     * @param name        名字
     * @param code        代码
     * @param status      状态
     * @return {@link IPage}<{@link Dictionary}>
     */
    @GetMapping
    @SysLog(value = "字典查询")
    public IPage<Dictionary> query(PageRequest pageRequest, String name, String code, Boolean status) {
        return this.dictionaryService.page(pageRequest.buildPage(),
                Wraps.<Dictionary>lbQ().eq(Dictionary::getStatus, status)
                        .like(Dictionary::getCode, code).like(Dictionary::getName, name));
    }

    /**
     * 字典新增
     *
     * @param dto dto
     */
    @PostMapping
    @SysLog(value = "字典新增")
    public void save(@Validated @RequestBody DictionaryDTO dto) {
        this.dictionaryService.addDictionary(DICTIONARY_DTO_2_PO_CONVERTS.convert(dto));
    }

    /**
     * 字典编辑
     *
     * @param id  id
     * @param dto dto
     */
    @PutMapping("/{id}")
    @SysLog(value = "字典编辑")
    public void edit(@PathVariable Long id, @Validated @RequestBody DictionaryDTO dto) {
        this.dictionaryService.editDictionary(DICTIONARY_DTO_2_PO_CONVERTS.convert(dto, id));
    }

    /**
     * 删除指定字典项
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除指定字典项", request = true)
    public void del(@PathVariable Long id) {
        this.dictionaryService.deleteById(id);
    }


    /**
     * 字典列表
     *
     * @param dictionaryCode 字典代码
     * @return {@link List}<{@link DictionaryItem}>
     */
    @GetMapping("/{dictionary_code}/list")
    public List<DictionaryItem> list(@PathVariable("dictionary_code") String dictionaryCode) {
        return this.dictionaryItemService.list(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getStatus, true).eq(DictionaryItem::getDictionaryCode, dictionaryCode));
    }
}
