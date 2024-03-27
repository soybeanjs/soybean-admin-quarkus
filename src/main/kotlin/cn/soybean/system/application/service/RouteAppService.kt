package cn.soybean.system.application.service

import cn.soybean.framework.common.consts.DbConstants
import cn.soybean.framework.common.util.isSuperUser
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.entity.toMenuRespVO
import cn.soybean.system.domain.service.MenuService
import cn.soybean.system.interfaces.convert.convertToMenuRespVO
import cn.soybean.system.interfaces.vo.MenuRespVO
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteAppService(private val menuService: MenuService) {

    fun getUserRoutes(userId: Long?): Uni<Map<String, Any>> = userId?.let { nonNullUserId ->
        val menusUni: Uni<List<SystemMenuEntity>> = when {
            isSuperUser(nonNullUserId) -> menuService.all()
            else -> menuService.allByUserId(nonNullUserId)
        }

        menusUni.map { menuItems ->
            val menuRoutes = buildTree(
                items = menuItems,
                idSelector = { it.id ?: 0L },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.convertToMenuRespVO().apply { this.children = children }
                }
            )
            mapOf("routes" to menuRoutes, "home" to "home")
        }
    } ?: Uni.createFrom().item(mapOf("routes" to Unit, "home" to "home"))

    fun getMenuList(userId: Long?): Uni<List<MenuRespVO>> = userId?.let { nonNullUserId ->
        val menusUni: Uni<List<SystemMenuEntity>> = when {
            isSuperUser(nonNullUserId) -> menuService.all()
            else -> menuService.allByUserId(nonNullUserId)
        }

        menusUni.map { menuItems ->
            buildTree(
                items = menuItems,
                idSelector = { it.id ?: 0L },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.toMenuRespVO().apply { this.children = children }
                }
            )
        }
    } ?: Uni.createFrom().item(emptyList())

    private fun <T, R> buildTree(
        items: List<T>,
        idSelector: (T) -> Long,
        parentIdSelector: (T) -> Long,
        rootId: Long,
        orderSelector: (T) -> Int,
        transform: (T, children: List<R>) -> R
    ): List<R> {
        val childrenByParentId = items.groupBy(parentIdSelector)

        fun buildNode(item: T): R {
            val children = childrenByParentId[idSelector(item)]
                ?.sortedBy(orderSelector)
                ?.map(::buildNode)
                .orEmpty()
            return transform(item, children)
        }

        return childrenByParentId[rootId]
            ?.sortedBy(orderSelector)
            ?.map(::buildNode)
            .orEmpty()
    }

    fun deleteRoute(id: List<Long>): Uni<Boolean> = TODO()
}