package com.soybean.framework.security.client;

import com.soybean.framework.security.client.annotation.IgnoreAuthorize;
import org.slf4j.Logger;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Objects;

/**
 * 忽略授权util
 *
 * @author wenxina
 * @date 2022/07/12
 */
public class IgnoreAuthorizeUtil {

    /**
     * 忽略授权
     *
     * @param registry 注册表
     * @param value    价值
     * @param key      关键
     * @param log      日志
     */
    static void resolveIgnoreAuthorize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry, HandlerMethod value, RequestMappingInfo key, Logger log) {
        IgnoreAuthorize annotation = value.getMethodAnnotation(IgnoreAuthorize.class);
        if (log.isDebugEnabled()) {
            log.debug("[key] - [{}] - [value] - [{}]", key, value);
        }
        if (annotation != null && !annotation.web()) {
            final PatternsRequestCondition patternsCondition = key.getPatternsCondition();
            if (Objects.nonNull(patternsCondition)) {
                patternsCondition.getPatterns().forEach(url -> registry.antMatchers(url).permitAll());
            }
            final PathPatternsRequestCondition pathPatternsCondition = key.getPathPatternsCondition();
            if (Objects.nonNull(pathPatternsCondition)) {
                pathPatternsCondition.getPatterns().forEach(url -> registry.antMatchers(url.getPatternString()).permitAll());
            }
        }
    }

}
