package cn.soybean.system.domain.entity

import cn.soybean.framework.common.base.BaseTenantEntity
import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.consts.enums.DbEnums
import cn.soybean.framework.common.exception.ErrorCode
import cn.soybean.framework.common.exception.ServiceException
import io.quarkus.elytron.security.common.BcryptUtil
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.smallrye.mutiny.Uni
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "sys_user", indexes = [
        Index(columnList = "tenant_id"),
        Index(columnList = "account_name"),
        Index(columnList = "email"),
        Index(columnList = "phone_number")
    ]
)
class SystemUserEntity(

    /**
     * 账号名称
     */
    @Column(name = "account_name", nullable = false, length = DbConstants.LENGTH_20)
    var accountName: String? = null,

    /**
     * 账号密码
     */
    @Column(name = "account_password")
    var accountPassword: String? = null,

    /**
     * 用户昵称
     */
    @Column(name = "nick_name", length = DbConstants.LENGTH_20)
    var nickName: String? = null,

    /**
     * 个人资料
     */
    @Column(name = "personal_profile")
    var personalProfile: String? = null,

    /**
     * 用户邮箱
     */
    @Column(name = "email", length = DbConstants.LENGTH_64)
    var email: String? = null,

    /**
     * 用户手机号码国家代码
     */
    @Column(name = "country_code", length = DbConstants.LENGTH_5)
    var countryCode: String = DbEnums.CountryInfo.CN.countryCode,

    /**
     * 用户手机号码国际区号
     */
    @Column(name = "phone_code", length = DbConstants.LENGTH_5)
    var phoneCode: String = DbEnums.CountryInfo.CN.phoneCode,

    /**
     * 用户手机号码
     */
    @Column(name = "phone_number", length = DbConstants.LENGTH_20)
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
     * 部门ID
     */
    @Column(name = "dept_id")
    var deptId: Long? = null,

    /**
     * 是否内置
     */
    @Column(name = "builtin", nullable = false)
    val builtin: Boolean = false,

    /**
     * 用户账号状态
     */
    @Column(name = "status", nullable = false)
    var status: DbEnums.Status = DbEnums.Status.ENABLED
) : BaseTenantEntity() {
    companion object : PanacheCompanion<SystemUserEntity> {
        private fun findByAccountNameOrEmailOrPhoneNumber(username: String, tenantId: Long): Uni<SystemUserEntity> =
            find(
                "tenantId = ?1 and (accountName = ?2 or email = ?2 or phoneNumber = ?2)",
                tenantId,
                username
            ).singleResult()

        fun pwdAuthenticate(userName: String, password: String, tenantId: Long): Uni<SystemUserEntity> =
            findByAccountNameOrEmailOrPhoneNumber(userName, tenantId)
                .onItem().ifNull().failWith(ServiceException(ErrorCode.ACCOUNT_NOT_FOUND))
                .flatMap { verifyUserCredentials(it, password) }

        private fun verifyUserCredentials(user: SystemUserEntity, password: String): Uni<SystemUserEntity> = when {
            !BcryptUtil.matches(password, user.accountPassword) -> Uni.createFrom()
                .failure(ServiceException(ErrorCode.ACCOUNT_CREDENTIALS_INVALID))

            user.status == DbEnums.Status.DISABLED -> Uni.createFrom()
                .failure(ServiceException(ErrorCode.ACCOUNT_DISABLED))

            else -> Uni.createFrom().item(user)
        }
    }
}