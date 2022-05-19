package com.soybean.uaa.repository;

import com.soybean.framework.db.configuration.dynamic.annotation.TenantDS;
import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.common.Dictionary;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 字典类型
 * </p>
 *
 * @author wenxina
 */
@TenantDS
@Repository
public interface DictionaryMapper extends SuperMapper<Dictionary> {

}
