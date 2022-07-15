package com.soybean.uaa.domain.entity.log;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 登录日志
 * </p>
 *
 * @author wenxina
 * @since 2019-11-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("common_login_log")
public class LoginLog {

    private static final long serialVersionUID = 1L;
    /**
     * 创建时间（依托数据库功能）
     */
    @TableField(value = "created_time")
    protected LocalDateTime createdTime;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录IP
     */
    @Length(max = 50, message = "登录IP长度不能超过50")
    private String ip;
    /**
     * 登录人ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 登录人姓名
     */
    @Length(max = 50, message = "登录人姓名长度不能超过50")
    private String name;
    /**
     * 登录人账号
     */
    @Length(max = 30, message = "登录人账号长度不能超过30")
    private String principal;
    /**
     * 平台类型
     */
    private String platform;
    /**
     * 引擎类型
     */
    private String engine;
    /**
     * 引擎版本
     */
    private String engineVersion;
    /**
     * 浏览器名称
     */
    @Length(max = 255, message = "浏览器名称长度不能超过255")
    @TableField(value = "browser", condition = LIKE)
    private String browser;
    /**
     * 浏览器版本
     */
    @Length(max = 255, message = "浏览器版本长度不能超过255")
    @TableField(value = "browser_version", condition = LIKE)
    private String browserVersion;
    /**
     * 操作系统
     */
    @Length(max = 255, message = "操作系统长度不能超过255")
    @TableField(value = "os", condition = LIKE)
    private String os;
    /**
     * 登录地点
     */
    @Length(max = 50, message = "登录地点长度不能超过50")
    @TableField(value = "location", condition = LIKE)
    private String location;
    private String clientId;

}
