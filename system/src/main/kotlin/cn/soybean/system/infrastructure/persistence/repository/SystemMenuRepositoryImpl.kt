package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.enums.DbEnums
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.repository.SystemMenuRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemMenuRepositoryImpl : SystemMenuRepository, PanacheRepositoryBase<SystemMenuEntity, String> {
    override fun all(): Uni<List<SystemMenuEntity>> = listAll()

    override fun allByUserId(userId: String): Uni<List<SystemMenuEntity>> = list(
        """
                    select m from SystemMenuEntity m
                    left join SystemRoleMenuEntity rm on m.id = rm.menuId
                    left join SystemUserRoleEntity ur on rm.roleId = ur.roleId
                    where ur.userId = ?1 and m.status = ?2
                """,
        userId,
        DbEnums.Status.ENABLED
    )

    override fun saveOrUpdate(entity: SystemMenuEntity): Uni<SystemMenuEntity> = persist(entity)
    override fun getById(id: String): Uni<SystemMenuEntity> = findById(id)
} 