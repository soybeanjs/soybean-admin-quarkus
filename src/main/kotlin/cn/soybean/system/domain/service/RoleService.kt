package cn.soybean.system.domain.service

import cn.soybean.system.domain.repository.SystemRoleRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleService(private val systemRoleRepository: SystemRoleRepository) {

    fun getRoleCodesByUserId(userId: Long): Uni<Set<String>> = systemRoleRepository.getRoleCodesByUserId(userId)
}