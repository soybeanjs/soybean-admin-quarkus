package cn.soybean.framework.common.base

import cn.soybean.framework.common.util.LoginHelper
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
                if (obj.createBy == null) loginHelper.getUserId().also { obj.createBy = it }
                if (obj.createAccountName.isNullOrBlank()) loginHelper.getAccountName()
                    .also { obj.createAccountName = it }
                LocalDateTime.now().also { obj.createTime = it }
            }
        }
    }

    @PreUpdate
    fun setUpdatedInfo(obj: Any) {
        when (obj) {
            is BaseEntity -> {
                if (obj.updateBy == null) loginHelper.getUserId().also { obj.updateBy = it }
                if (obj.updateAccountName.isNullOrBlank()) loginHelper.getAccountName()
                    .also { obj.updateAccountName = it }
                LocalDateTime.now().also { obj.updateTime = it }
            }
        }
    }
}