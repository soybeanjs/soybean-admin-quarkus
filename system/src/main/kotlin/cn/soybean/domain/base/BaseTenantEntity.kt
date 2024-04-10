package cn.soybean.domain.base

import cn.soybean.infrastructure.persistence.listeners.BaseTenantEntityListener
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(BaseTenantEntityListener::class)
abstract class BaseTenantEntity : BaseEntity() {

    /**
     * 租户ID
     */
//    @TenantId//SCHEMA租户模式下不需要定义此字段和注解 DISCRIMINATOR租户模式下启用
    @Column(name = "tenant_id", nullable = false)
    open var tenantId: String? = null
}