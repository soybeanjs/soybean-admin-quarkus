package com.soybean.framework.db.configuration.permission;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.soybean.framework.db.mybatis.auth.permission.aop.DataPermissionAnnotationAdvisor;
import com.soybean.framework.db.mybatis.auth.permission.db.DataPermissionDatabaseInterceptor;
import com.soybean.framework.db.mybatis.auth.permission.rule.DataPermissionRule;
import com.soybean.framework.db.mybatis.auth.permission.rule.DataPermissionRuleFactory;
import com.soybean.framework.db.mybatis.auth.permission.rule.DataPermissionRuleFactoryImpl;
import com.soybean.framework.db.util.MyBatisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 数据权限的自动配置类
 *
 * @author 芋道源码
 */
@Configuration
public class DataPermissionAutoConfiguration {

    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    @Bean
    public DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
                                                                               List<DataPermissionRule> rules) {
        // 创建 DataPermissionDatabaseInterceptor 拦截器
        DataPermissionRuleFactory ruleFactory = dataPermissionRuleFactory(rules);
        DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }

}
