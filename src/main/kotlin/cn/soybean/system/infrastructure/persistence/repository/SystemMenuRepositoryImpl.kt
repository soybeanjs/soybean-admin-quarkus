package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.repository.SystemMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemMenuRepositoryImpl : SystemMenuRepository, PanacheRepository<SystemMenuEntity> {
    override fun all(): Uni<List<SystemMenuEntity>> = listAll()

    override fun allByUserId(userId: Long): Uni<List<SystemMenuEntity>> = list(
        """
                    select m from SystemMenuEntity m
                    left join SystemRoleMenuEntity rm on m.id = rm.menuId
                    left join SystemUserRoleEntity ur on rm.roleId = ur.roleId
                    where ur.userId = ?1 and m.status = ?2
                """,
        userId,
        DbEnums.Status.ENABLED
    )
} 