package com.soybean.uaa.service;


import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.common.DictionaryItem;

/**
 * <p>
 * 业务接口
 * 字典项
 * </p>
 *
 * @author wenxina
 * @since 2019-07-02
 */
public interface DictionaryItemService extends SuperService<DictionaryItem> {


    /**
     * 添加字典项
     *
     * @param dictionaryId 字典ID
     * @param item         字典项
     */
    void addDictionaryItem(Long dictionaryId, DictionaryItem item);

    /**
     * 修改字典项
     *
     * @param dictionaryId 字典ID
     * @param item         字典项
     */
    void editDictionaryItem(Long dictionaryId, DictionaryItem item);
}
