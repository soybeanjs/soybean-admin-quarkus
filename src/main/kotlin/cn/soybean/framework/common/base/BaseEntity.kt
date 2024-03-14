package cn.soybean.framework.common.base

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(BaseEntityListener::class)
abstract class BaseEntity : PanacheEntity(), Serializable {

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    open lateinit var createTime: LocalDateTime

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    open var updateTime: LocalDateTime? = null

    /**
     * 创建人
     */
    @Column(name = "create_by")
    open var createBy: Long? = null

    /**
     * 创建人名称
     */
    @Column(name = "create_account_name")
    open var createAccountName: String? = null

    /**
     * 更新人
     */
    @Column(name = "update_by")
    open var updateBy: Long? = null

    /**
     * 更新人名称
     */
    @Column(name = "update_account_name")
    open var updateAccountName: String? = null
}