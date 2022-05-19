package com.soybean.uaa.service.impl;

import com.soybean.framework.db.mybatis.SuperServiceImpl;
import com.soybean.uaa.domain.entity.baseinfo.OAuthClientDetails;
import com.soybean.uaa.repository.OAuthClientDetailsMapper;
import com.soybean.uaa.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wenxina
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl extends SuperServiceImpl<OAuthClientDetailsMapper, OAuthClientDetails> implements ApplicationService {


}
