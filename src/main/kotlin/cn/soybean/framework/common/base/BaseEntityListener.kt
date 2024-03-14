package cn.soybean.framework.common.base

import cn.soybean.framework.common.utils.LoginHelper
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
            is BaseEntity -> {
                obj.createBy = loginHelper.getUserId()
                obj.createAccountName = loginHelper.getAccountName()
                obj.createTime = LocalDateTime.now()
            }
        }
    }

    @PreUpdate
    fun setUpdatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity -> {
                obj.updateBy = loginHelper.getUserId()
                obj.updateAccountName = loginHelper.getAccountName()
                obj.updateTime = LocalDateTime.now()
            }
        }
    }
}