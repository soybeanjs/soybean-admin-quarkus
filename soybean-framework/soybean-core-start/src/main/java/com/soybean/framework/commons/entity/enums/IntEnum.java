package com.soybean.framework.commons.entity.enums;

/**
 * 基础 enum 类，主要用来给其它类进行继承
 *
 * @author wenxina
 * @since 2019-07-09
 */
public interface IntEnum {

    /**
     * type
     *
     * @return type
     */
    Integer type();

    /**
     * 描述
     *
     * @return desc
     */
    String desc();
}
