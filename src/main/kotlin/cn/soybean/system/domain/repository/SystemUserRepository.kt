package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemUserEntity
import io.smallrye.mutiny.Uni

interface SystemUserRepository {
    fun findByAccountNameOrEmailOrPhoneNumber(username: String, tenantId: Long): Uni<SystemUserEntity>
}