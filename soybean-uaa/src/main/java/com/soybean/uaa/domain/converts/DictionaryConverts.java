package com.soybean.uaa.domain.converts;

import com.soybean.framework.db.page.BasePageConverts;
import com.soybean.uaa.domain.dto.DictionaryDTO;
import com.soybean.uaa.domain.dto.DictionaryItemDTO;
import com.soybean.uaa.domain.entity.common.Dictionary;
import com.soybean.uaa.domain.entity.common.DictionaryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author wenxina
 */
@Slf4j
public class DictionaryConverts {

    public static final DictionaryDto2PoConverts DICTIONARY_DTO_2_PO_CONVERTS = new DictionaryDto2PoConverts();
    public static final DictionaryItemDto2ItemPoConverts DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS = new DictionaryItemDto2ItemPoConverts();

    public static class DictionaryDto2PoConverts implements BasePageConverts<DictionaryDTO, Dictionary> {
        /**
         * 类型转换
         *
         * @param source 原对象
         * @return 目标对象
         */
        @Override
        public Dictionary convert(DictionaryDTO source) {
            if (source == null) {
                return null;
            }
            Dictionary target = new Dictionary();
            BeanUtils.copyProperties(source, target);
            return target;
        }

        @Override
        public Dictionary convert(DictionaryDTO source, Long id) {
            if (source == null) {
                return null;
            }
            Dictionary target = new Dictionary();
            BeanUtils.copyProperties(source, target);
            target.setId(id);
            return target;
        }
    }

    public static class DictionaryItemDto2ItemPoConverts implements BasePageConverts<DictionaryItemDTO, DictionaryItem> {

        @Override
        public DictionaryItem convert(DictionaryItemDTO source) {
            if (source == null) {
                return null;
            }
            DictionaryItem target = new DictionaryItem();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

}
