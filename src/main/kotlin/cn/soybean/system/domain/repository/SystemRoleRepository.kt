package cn.soybean.system.domain.repository

import io.smallrye.mutiny.Uni

interface SystemRoleRepository {
    fun getRoleCodesByUserId(userId: Long): Uni<Set<String>>
}