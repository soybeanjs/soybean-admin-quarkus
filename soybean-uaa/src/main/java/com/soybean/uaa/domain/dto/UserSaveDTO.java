package com.soybean.uaa.domain.dto;

import com.soybean.uaa.domain.enums.Sex;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实体类
 * 用户
 *
 * @author wenxina
 * @since 2020-02-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Length(max = 30, message = "账号长度不能超过{max}")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(max = 64, message = "密码长度不能超过{max}")
    private String password;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(max = 50, message = "姓名长度不能超过50")
    private String nickName;
    /**
     * 组织ID
     * #c_core_org
     */
    private Long orgId;
    /**
     * 岗位ID
     * #c_core_station
     */
    private Long stationId;
    /**
     * 邮箱
     */
    @Length(max = 255, message = "邮箱长度不能超过255")
    private String email;
    /**
     * 手机
     */
    @Length(max = 20, message = "手机长度不能超过20")
    private String mobile;
    /**
     * 性别
     * #Sex{W:女;M:男;N:未知}
     */
    private Sex sex;
    /**
     * 状态
     * 1启用 0禁用
     */
    private Boolean status;
    /**
     * 头像
     */
    @Length(max = 255, message = "头像长度不能超过255")
    private String avatar;
    /**
     * 民族
     */
    @Length(max = 20, message = "民族长度不能超过20")
    private String nation;
    /**
     * 学历
     */
    @Length(max = 20, message = "学历长度不能超过20")
    private String education;
    /**
     * 职位状态
     */
    @Length(max = 20, message = "职位状态长度不能超过{max}")
    private String positionStatus;
    /**
     * 工作描述
     * 比如：  市长、管理员、局长等等   用于登陆展示
     */
    @Length(max = 255, message = "描述长度不能超过{max}")
    private String description;


}
