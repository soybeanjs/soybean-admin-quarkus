package com.soybean.framework.redis.plus.anontation;

import java.lang.annotation.*;

/**
 * 缓存 Key 的参数
 *
 * @author wenxina
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}