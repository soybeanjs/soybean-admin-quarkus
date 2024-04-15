package cn.soybean.infrastructure.persistence.listeners

import cn.soybean.domain.base.BaseTenantEntity
import cn.soybean.infrastructure.security.LoginHelper
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
                if (obj.tenantId == null) loginHelper.getTenantId().also { obj.tenantId = it }
            }
        }
    }
}