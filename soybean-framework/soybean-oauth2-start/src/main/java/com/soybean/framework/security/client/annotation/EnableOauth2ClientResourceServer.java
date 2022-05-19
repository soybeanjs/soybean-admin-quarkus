package com.soybean.framework.security.client.annotation;

import com.soybean.framework.security.client.SecurityBeanDefinitionRegistrar;
import com.soybean.framework.security.feign.HeaderFeignConfiguration;
import com.soybean.framework.security.feign.SecurityInnerServiceAspect;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 资源服务注解
 *
 * @author wenxina
 * @since 2019-04-03
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({SecurityBeanDefinitionRegistrar.class,
        HeaderFeignConfiguration.class,
        SecurityInnerServiceAspect.class})
public @interface EnableOauth2ClientResourceServer {

    /**
     * false 选用     UserInfoTokenServices
     * true  选用     RemoteTokenServices
     *
     * @return true | false
     */
    boolean preferTokenInfo() default false;

}
