package com.soybean.framework.commons.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 增强实体类
 *
 * @author wenxina
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class SuperEntity<T> extends Entity<T> {

    public static final String UPDATE_TIME = "lastModifiedTime";
    public static final String UPDATE_USER = "lastModifiedBy";
    public static final String UPDATE_USER_NAME = "lastModifiedName";

    public static final String UPDATE_TIME_COLUMN = "last_modified_time";
    public static final String UPDATE_USER_COLUMN = "last_modified_by";
    public static final String UPDATE_USER_NAME_COLUMN = "last_modified_name";

    private static final long serialVersionUID = 5169873634279173683L;

    @TableField(value = UPDATE_TIME_COLUMN)
    protected LocalDateTime lastModifiedTime;

    @TableField(value = UPDATE_USER_COLUMN, fill = FieldFill.INSERT_UPDATE)
    protected T lastModifiedBy;


    @TableField(value = UPDATE_USER_NAME_COLUMN, fill = FieldFill.INSERT_UPDATE)
    protected String lastModifiedName;

}
