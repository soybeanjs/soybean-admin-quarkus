package cn.soybean.system.application.service

import cn.soybean.application.command.CommandInvoker
import cn.soybean.application.exceptions.ErrorCode
import cn.soybean.application.exceptions.ServiceException
import cn.soybean.domain.enums.DbEnums
import cn.soybean.infrastructure.config.consts.DbConstants
import cn.soybean.system.application.command.CreateRouteCommand
import cn.soybean.system.application.command.DeleteRouteCommand
import cn.soybean.system.application.command.UpdateRouteCommand
import cn.soybean.system.application.query.RouteByIdQuery
import cn.soybean.system.application.query.service.RouteQueryService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RouteService(private val routeQueryService: RouteQueryService, private val commandInvoker: CommandInvoker) {

    fun createRoute(command: CreateRouteCommand): Uni<Boolean> =
        validateParentMenu(command.parentId, null).flatMap {
            commandInvoker.dispatch<CreateRouteCommand, Boolean>(
                command
            ).map { it }
        }

    fun updateRoute(command: UpdateRouteCommand): Uni<Boolean> =
        validateParentMenu(command.parentId, command.id).flatMap {
            commandInvoker.dispatch<UpdateRouteCommand, Boolean>(
                command
            ).map { it }
        }

    fun deleteRoute(command: DeleteRouteCommand): Uni<Boolean> =
        commandInvoker.dispatch<DeleteRouteCommand, Boolean>(command).map { it }

    private fun validateParentMenu(parentId: String?, childId: String?): Uni<Unit> = when (parentId) {
        null, DbConstants.PARENT_ID_ROOT -> Uni.createFrom().item(Unit)
        childId -> Uni.createFrom().failure(ServiceException(ErrorCode.SELF_PARENT_MENU_NOT_ALLOWED))
        else -> routeQueryService.handle(RouteByIdQuery(parentId))
            .onItem().ifNull().failWith(ServiceException(ErrorCode.PARENT_MENU_NOT_FOUND))
            .flatMap { menu ->
                when {
                    menu.menuType != DbEnums.MenuItemType.DIRECTORY -> Uni.createFrom()
                        .failure(ServiceException(ErrorCode.PARENT_MENU_TYPE_INVALID))

                    else -> Uni.createFrom().item(Unit)
                }
            }
    }
}