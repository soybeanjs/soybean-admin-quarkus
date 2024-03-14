package cn.soybean.system.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.enums.DbEnums
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sys_user")
class SystemUserEntity(

    /**
     * 账号名称
     */
    @Column(name = "account_name", nullable = false, length = 64)
    var accountName: String? = null,

    /**
     * 账号密码
     */
    @Column(name = "account_password")
    var accountPassword: String? = null,

    /**
     * 用户昵称
     */
    @Column(name = "nick_name", length = 20)
    var nickName: String? = null,

    /**
     * 个人资料
     */
    @Column(name = "personal_profile")
    var personalProfile: String? = null,

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    var email: String? = null,

    /**
     * 用户手机号码国家代码
     */
    @Column(name = "country_code", length = 10)
    var countryCode: String = DbEnums.CountryInfo.CN.countryCode,

    /**
     * 用户手机号码国际区号
     */
    @Column(name = "phone_code", length = 10)
    var phoneCode: String = DbEnums.CountryInfo.CN.phoneCode,

    /**
     * 用户手机号码
     */
    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    /**
     * 用户性别
     */
    @Column(name = "gender")
    var gender: DbEnums.Gender? = null,

    /**
     * 用户头像
     */
    @Column(name = "avatar")
    var avatar: String? = null,

    /**
     * 用户账号状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED
) : BaseTenantEntity()