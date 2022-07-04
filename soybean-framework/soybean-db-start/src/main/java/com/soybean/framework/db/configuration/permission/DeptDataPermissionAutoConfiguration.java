package com.soybean.framework.db.configuration.permission;

import com.soybean.framework.db.mybatis.auth.permission.rule.dept.DeptDataPermissionRule;
import com.soybean.framework.db.mybatis.auth.permission.rule.dept.DeptDataPermissionRuleCustomizer;
import com.soybean.framework.db.mybatis.auth.permission.service.DataScopeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 基于部门的数据权限 AutoConfiguration
 *
 * @author 芋道源码
 */
@Configuration
//@ConditionalOnClass(UserInfoDetails.class)
//@ConditionalOnBean(value = {DataScopeService.class, DeptDataPermissionRuleCustomizer.class})
public class DeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(DataScopeService dataScopeService,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(dataScopeService);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
