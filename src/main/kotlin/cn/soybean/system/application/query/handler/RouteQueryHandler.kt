package cn.soybean.system.application.query.handler

import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.application.convert.convertToMenuRespVO
import cn.soybean.system.application.query.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.RouteByIdQuery
import cn.soybean.system.application.query.service.RouteQueryService
import cn.soybean.system.application.service.MenuService
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.entity.toMenuRespVO
import cn.soybean.system.interfaces.rest.vo.MenuRespVO
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteQueryHandler(private val menuService: MenuService) : RouteQueryService {

    override fun handle(query: GetRoutesByUserIdQuery): Uni<Map<String, Any>> {
        val (userId) = query
        val menusUni: Uni<List<SystemMenuEntity>> = getRoutesByUserId(userId)

        return menusUni.map { menuItems ->
            val menuRoutes = buildTree(
                items = menuItems,
                idSelector = { it.id },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.convertToMenuRespVO().apply { this.children = children }
                }
            )
            mapOf("routes" to menuRoutes, "home" to "home")
        }
    }

    override fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuRespVO>> {
        val (userId) = query
        val menusUni: Uni<List<SystemMenuEntity>> = getRoutesByUserId(userId)

        return menusUni.map { menuItems ->
            buildTree(
                items = menuItems,
                idSelector = { it.id },
                parentIdSelector = { it.parentId ?: DbConstants.PARENT_ID_ROOT },
                rootId = DbConstants.PARENT_ID_ROOT,
                orderSelector = { it.order ?: 0 },
                transform = { item, children ->
                    item.toMenuRespVO().apply { this.children = children }
                }
            )
        }
    }

    override fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity> = menuService.getById(query.id)

    private fun getRoutesByUserId(userId: String): Uni<List<SystemMenuEntity>> = when {
        isSuperUser(userId) -> menuService.all()
        else -> menuService.allByUserId(userId)
    }

    private fun <T, R> buildTree(
        items: List<T>,
        idSelector: (T) -> String,
        parentIdSelector: (T) -> String,
        rootId: String,
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
}