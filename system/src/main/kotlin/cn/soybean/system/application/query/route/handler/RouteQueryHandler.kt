package cn.soybean.system.application.query.route.handler

import cn.soybean.interfaces.rest.util.isSuperUser
import cn.soybean.system.application.convert.convertToMenuResponse
import cn.soybean.system.application.query.route.GetRoutesByUserIdQuery
import cn.soybean.system.application.query.route.ListTreeRoutesByUserIdQuery
import cn.soybean.system.application.query.route.RouteByConstantQuery
import cn.soybean.system.application.query.route.RouteByIdBuiltInQuery
import cn.soybean.system.application.query.route.RouteByIdQuery
import cn.soybean.system.application.query.route.service.RouteQueryService
import cn.soybean.system.domain.config.DbConstants
import cn.soybean.system.domain.entity.SystemMenuEntity
import cn.soybean.system.domain.entity.toMenuResponse
import cn.soybean.system.domain.repository.SystemMenuRepository
import cn.soybean.system.interfaces.rest.dto.response.route.MenuResponse
import cn.soybean.system.interfaces.rest.dto.response.route.MenuRoute
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteQueryHandler(private val systemMenuRepository: SystemMenuRepository) : RouteQueryService {

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
                    item.convertToMenuResponse().apply { this.children = children }
                }
            )
            mapOf("routes" to menuRoutes, "home" to "home")
        }
    }

    override fun handle(query: ListTreeRoutesByUserIdQuery): Uni<List<MenuResponse>> {
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
                    item.toMenuResponse().apply { this.children = children }
                }
            )
        }
    }

    override fun handle(query: RouteByIdQuery): Uni<SystemMenuEntity> = systemMenuRepository.getById(query.id)
    override fun handle(query: RouteByIdBuiltInQuery): Uni<Boolean> =
        systemMenuRepository.getById(query.id).map { it?.builtIn ?: true }

    override fun handle(query: RouteByConstantQuery): Uni<List<MenuRoute>> =
        systemMenuRepository.findAllByConstant(query.constant)
            .map { menuList -> menuList.map { it.convertToMenuResponse() } }

    private fun getRoutesByUserId(userId: String): Uni<List<SystemMenuEntity>> = when {
        isSuperUser(userId) -> systemMenuRepository.all()
        else -> systemMenuRepository.allByUserId(userId)
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
