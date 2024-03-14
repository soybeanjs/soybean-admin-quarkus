package cn.soybean.framework.common.base

import cn.soybean.framework.common.utils.LoginHelper
import io.quarkus.arc.Arc
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.PrePersist

@RegisterForReflection
class BaseTenantEntityListener {

    private val loginHelper: LoginHelper by lazy {
        Arc.container().instance(LoginHelper::class.java).get()
    }

    @PrePersist
    fun setTenantId(obj: Any) {
        when (obj) {
            is BaseTenantEntity -> {
                if (obj.tenantId == null) {
                    obj.tenantId = loginHelper.getTenantId()
                }
            }
        }
    }
}