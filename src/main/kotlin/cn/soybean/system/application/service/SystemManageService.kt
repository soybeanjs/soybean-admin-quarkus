package cn.soybean.system.application.service

import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.util.isSuperUser
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.interfaces.convert.convertToMenuRespVO
import cn.soybean.system.interfaces.vo.MenuRespVO
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemManageService {

    fun getUserRoutes(userId: Long?): Uni<Map<String, Any>> = userId?.let { nonNullUserId ->
        when {
            isSuperUser(nonNullUserId) -> SystemMenuEntity.listAll()
                .map { mapOf("routes" to buildMenuTree(it), "home" to "home") }

            else -> SystemMenuEntity.listAllByUserId(userId)
                .map { mapOf("routes" to buildMenuTree(it), "home" to "home") }
        }
    } ?: Uni.createFrom().item(mapOf("routes" to Unit, "home" to "home"))

    private fun buildMenuTree(menuList: List<SystemMenuEntity>): List<MenuRespVO> {
        if (menuList.isEmpty()) return emptyList()

        val treeNodeMap = menuList.associate {
            it.id to it.convertToMenuRespVO()
        }.toMutableMap()

        treeNodeMap.values.forEach { childNode ->
            if (childNode.parentId != DbConstants.PARENT_ID_ROOT) {
                treeNodeMap[childNode.parentId]?.children =
                    treeNodeMap[childNode.parentId]?.children.orEmpty() + childNode
            }
        }

        // 返回根节点列表
        return treeNodeMap.values.filter { it.parentId == DbConstants.PARENT_ID_ROOT }
    }
}