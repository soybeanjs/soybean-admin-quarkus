package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemTenantEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni

interface SystemTenantRepository {
    fun findByName(name: String): Uni<SystemTenantEntity>
    fun getTenantList(query: String, params: Parameters): PanacheQuery<SystemTenantEntity>
    fun existsByName(name: String): Uni<Boolean>
    fun getById(id: String): Uni<SystemTenantEntity>
    fun saveOrUpdate(entity: SystemTenantEntity): Uni<SystemTenantEntity>
}