package com.soybean.framework.commons.entity;


import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形实体
 *
 * @author wenxina
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class TreeEntity<E, T extends Serializable> extends SuperEntity<T> {

    private static final long serialVersionUID = 1788431089674465975L;
    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    @TableField(value = "label", condition = SqlCondition.LIKE)
    protected String label;

    /**
     * 父ID
     */
    @TableField(value = "parent_id")
    protected T parentId;
    /**
     * 排序
     */
    @TableField(value = "`sequence`")
    protected Integer sequence;
    @TableField(exist = false)
    protected List<E> children = Lists.newArrayList();
    @TableField(exist = false)
    protected Integer size = 0;
    @TableField(value = "`tel`")
    private String tel;

    /**
     * 初始化子类
     */
    public void initChildren() {
        if (getChildren() == null) {
            this.setChildren(new ArrayList<>());
        }
    }
}
