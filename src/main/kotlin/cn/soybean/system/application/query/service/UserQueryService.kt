package cn.soybean.system.application.query.service

import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.PageUserQuery
import cn.soybean.system.interfaces.rest.vo.UserRespVO
import io.smallrye.mutiny.Uni

interface UserQueryService {
    fun handle(query: PageUserQuery): Uni<PageResult<UserRespVO>>
}