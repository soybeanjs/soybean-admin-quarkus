package com.soybean.uaa.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.framework.db.mybatis.DictionaryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * SexType
 *
 * @author wenxina
 * @since 2020-02-14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public enum Sex implements DictionaryEnum<Integer> {

    /**
     * 按钮
     */
    UNKONW(0, "未知"),
    /**
     * 男
     */
    MAN(1, "男"),
    /**
     * 女
     */
    WOMAN(2, "女"),
    ;
    @EnumValue
    @JsonValue
    private Integer type;

    private String desc;

    @JsonCreator
    public static Sex of(Integer type) {
        if (type == null) {
            return null;
        }
        for (Sex info : values()) {
            if (info.type.equals(type)) {
                return info;
            }
        }
        return null;
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(Sex val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }


}
