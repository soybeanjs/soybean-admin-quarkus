package com.soybean.uaa.repository;

import com.soybean.framework.db.mybatis.SuperMapper;
import com.soybean.uaa.domain.entity.baseinfo.OAuthClientDetails;
import org.springframework.stereotype.Repository;

/**
 * @author wenxina
 */
@Repository
public interface OAuthClientDetailsMapper extends SuperMapper<OAuthClientDetails> {


}
