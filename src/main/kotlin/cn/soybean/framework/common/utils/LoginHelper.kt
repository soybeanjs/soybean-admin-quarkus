package cn.soybean.framework.common.utils

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

    fun getUserId(): Long? = subject.toLongOrNull()

    fun getAccountName(): String = upn

    fun getTenantId(): Long? = tenant.toLongOrNull()

    companion object {
        const val TENANT_KEY: String = "tenantId"
        const val USER_KEY: String = "userId"
        const val DEPT_KEY: String = "deptId"
    }
}