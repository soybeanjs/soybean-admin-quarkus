package cn.soybean.infrastructure.security

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.jwt.Claim
import org.eclipse.microprofile.jwt.Claims

@RequestScoped
class LoginHelper {

    @Inject
    @Claim(standard = Claims.sub)
    private lateinit var subject: String

    @Inject
    @Claim(standard = Claims.upn)
    private lateinit var upn: String

    @Inject
    @Claim("tenantId")
    private lateinit var tenant: String

    @Inject
    @Claim(standard = Claims.groups)
    private lateinit var groups: Set<Any>

    fun getUserId(): Long? = subject.toLongOrNull()

    fun getAccountName(): String = upn

    fun getTenantId(): Long? = tenant.toLongOrNull()

    fun getRoles(): Set<Any> = groups

    companion object {
        const val TENANT_KEY: String = "tenantId"
        const val USER_KEY: String = "userId"
        const val DEPT_KEY: String = "deptId"
        const val USER_AVATAR: String = "avatar"
    }
}