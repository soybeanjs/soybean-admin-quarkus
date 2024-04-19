package cn.soybean.infrastructure.persistence.listeners

import cn.soybean.domain.base.BaseEntity
import cn.soybean.infrastructure.security.LoginHelper
import io.quarkus.arc.Arc
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@RegisterForReflection
class BaseEntityListener {

    private val loginHelper: LoginHelper by lazy {
        Arc.container().instance(LoginHelper::class.java).get()
    }

    @PrePersist
    fun setCreatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity -> obj.apply {
                createBy = createBy ?: loginHelper.getUserId()
                createAccountName = createAccountName.takeUnless { it.isNullOrBlank() } ?: loginHelper.getAccountName()
                createTime = LocalDateTime.now()
            }
        }
    }

    @PreUpdate
    fun setUpdatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity -> obj.apply {
                updateBy = updateBy ?: loginHelper.getUserId()
                updateAccountName = updateAccountName.takeUnless { it.isNullOrBlank() } ?: loginHelper.getAccountName()
                updateTime = LocalDateTime.now()
            }
        }
    }
}