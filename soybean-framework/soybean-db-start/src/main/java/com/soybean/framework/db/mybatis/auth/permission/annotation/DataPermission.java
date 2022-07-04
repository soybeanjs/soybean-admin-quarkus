package com.soybean.framework.db.mybatis.auth.permission.annotation;

import com.soybean.framework.db.mybatis.auth.permission.prop.DataScopeType;
import com.soybean.framework.db.mybatis.auth.permission.rule.DataPermissionRule;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * 可声明在类或者方法上，标识使用的数据权限规则
 *
 * @author 芋道源码
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 当前类或方法是否开启数据权限
     * 即使不添加 @DataPermission 注解，默认是开启状态
     * 可通过设置 enable 为 false 禁用
     */
    boolean enable() default true;

    /**
     * 限制范围为个人时的字段名称
     */

    String selfScopeName() default "created_by";

    /**
     * 权限范围
     */
    DataScopeType scopeType() default DataScopeType.SELF;

    /**
     * 生效的数据权限规则数组，优先级高于 {@link #excludeRules()}
     */
    Class<? extends DataPermissionRule>[] includeRules() default {};

    /**
     * 排除的数据权限规则数组，优先级最低
     */
    Class<? extends DataPermissionRule>[] excludeRules() default {};

}
