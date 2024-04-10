package cn.soybean.system.application.service

import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.repository.SystemMenuRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class MenuService(private val systemMenuRepository: SystemMenuRepository) {

    fun all(): Uni<List<SystemMenuEntity>> = systemMenuRepository.all()

    fun allByUserId(userId: String): Uni<List<SystemMenuEntity>> = systemMenuRepository.allByUserId(userId)

    fun getById(id: String): Uni<SystemMenuEntity> = systemMenuRepository.getById(id)
}