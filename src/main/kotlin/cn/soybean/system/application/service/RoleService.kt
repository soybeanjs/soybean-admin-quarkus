package cn.soybean.system.application.service

import cn.soybean.system.domain.entity.SystemRoleEntity
import cn.soybean.system.domain.repository.SystemRoleRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleService(private val systemRoleRepository: SystemRoleRepository) {

    fun getRoleCodesByUserId(userId: String): Uni<Set<String>> = systemRoleRepository.getRoleCodesByUserId(userId)

    fun getRoleList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemRoleEntity> =
        systemRoleRepository.getRoleList(query, sort, params)
}