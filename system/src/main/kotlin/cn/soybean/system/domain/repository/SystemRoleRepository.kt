package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemRoleEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Uni

interface SystemRoleRepository {
    fun getRoleCodesByUserId(userId: String): Uni<Set<String>>
    fun getRoleList(query: String, sort: Sort, params: Parameters): PanacheQuery<SystemRoleEntity>
    fun saveOrUpdate(entity: SystemRoleEntity): Uni<SystemRoleEntity>
    fun getById(id: String, tenantId: String): Uni<SystemRoleEntity?>
    fun existsByCode(code: String, tenantId: String): Uni<Boolean>
    fun delById(id: String, tenantId: String): Uni<Long>
    fun getByCode(tenantId: String, code: String): Uni<SystemRoleEntity>
}
