package com.soybean.uaa.service;


import com.soybean.framework.db.mybatis.SuperService;
import com.soybean.uaa.domain.entity.common.Dictionary;

/**
 * <p>
 * 业务接口
 * 字典类型
 * </p>
 *
 * @author wenxina
 */
public interface DictionaryService extends SuperService<Dictionary> {

    /**
     * 添加字典
     *
     * @param dictionary 字典信息
     */
    void addDictionary(Dictionary dictionary);

    /**
     * 删除字典
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 编辑字典
     *
     * @param dictionary 字典信息
     */
    void editDictionary(Dictionary dictionary);
}
