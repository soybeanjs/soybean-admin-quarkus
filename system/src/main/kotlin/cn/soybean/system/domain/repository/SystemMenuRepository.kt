package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemMenuEntity
import io.smallrye.mutiny.Uni

interface SystemMenuRepository {
    fun all(): Uni<List<SystemMenuEntity>>
    fun allByUserId(userId: String): Uni<List<SystemMenuEntity>>
    fun saveOrUpdate(entity: SystemMenuEntity): Uni<SystemMenuEntity>
    fun getById(id: String): Uni<SystemMenuEntity>
    fun delById(id: String): Uni<Boolean>
    fun findAllByConstant(constant: Boolean): Uni<List<SystemMenuEntity>>
}
