package com.soybean.framework.boot.db;

import com.soybean.framework.db.TenantEnvironment;
import com.soybean.framework.security.client.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 租户环境配置
 *
 * @author wenxina
 * @date 2022/07/04
 */
@Configuration
public class TenantEnvironmentConfiguration {

    @Bean
    public TenantEnvironment tenantEnvironment() {
        return new TenantEnvironment() {
            @Override
            public Long tenantId() {
                return SecurityUtils.getAuthInfo().getTenantId();
            }

            @Override
            public Long userId() {
                return SecurityUtils.getAuthInfo().getUserId();
            }

            @Override
            public String realName() {
                return SecurityUtils.getAuthInfo().getRealName();
            }

            @Override
            public boolean anonymous() {
                return SecurityUtils.anonymous();
            }
        };
    }

}
