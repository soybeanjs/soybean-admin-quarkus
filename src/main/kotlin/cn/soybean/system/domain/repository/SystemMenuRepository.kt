package cn.soybean.system.domain.repository

import cn.soybean.system.domain.entity.SystemMenuEntity
import io.smallrye.mutiny.Uni

interface SystemMenuRepository {
    fun all(): Uni<List<SystemMenuEntity>>
    fun allByUserId(userId: Long): Uni<List<SystemMenuEntity>>
}