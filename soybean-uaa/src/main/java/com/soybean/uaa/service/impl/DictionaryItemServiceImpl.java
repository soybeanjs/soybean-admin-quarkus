package com.soybean.uaa.service.impl;

import com.soybean.framework.commons.exception.CheckedException;
import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.framework.db.mybatis.conditions.Wraps;
import com.soybean.uaa.domain.entity.common.Dictionary;
import com.soybean.uaa.domain.entity.common.DictionaryItem;
import com.soybean.uaa.repository.DictionaryItemMapper;
import com.soybean.uaa.repository.DictionaryMapper;
import com.soybean.uaa.service.DictionaryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 业务实现类
 * 字典项
 * </p>
 *
 * @author wenxina
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryItemServiceImpl extends SuperServiceImpl<DictionaryItemMapper, DictionaryItem> implements DictionaryItemService {

    private final DictionaryMapper dictionaryMapper;


    @Override
    public void addDictionaryItem(Long dictionaryId, DictionaryItem item) {
        final long count = this.baseMapper.selectCount(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getValue, item.getValue())
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        if (count > 0) {
            throw CheckedException.badRequest("编码已存在");
        }
        final Dictionary dictionary = Optional.ofNullable(this.dictionaryMapper.selectById(dictionaryId))
                .orElseThrow(() -> CheckedException.notFound("码表不存在"));
        item.setDictionaryId(dictionaryId);
        item.setDictionaryCode(dictionary.getCode());
        this.baseMapper.insert(item);
    }

    @Override
    public void editDictionaryItem(Long dictionaryId, DictionaryItem item) {
        final long count = this.baseMapper.selectCount(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getValue, item.getValue())
                .ne(DictionaryItem::getId, item.getId())
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        if (count > 0) {
            throw CheckedException.badRequest("编码已存在");
        }
        this.baseMapper.updateById(item);
    }
}
