package cn.soybean.framework.interfaces.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import java.io.Serializable

open class BasePageParam(
    /**
     * See [io.quarkus.panache.common.Page.index]
     */
    @field:Parameter(name = "current", description = "页码,默认1", example = "1", required = false)
    @field:QueryParam("current")
    @field:NotNull(message = "页码不能为空")
    @field:Min(value = 1, message = "页码最小值为 1")
    var index: Int = PAGE_NO,

    /**
     * See [io.quarkus.panache.common.Page.size]
     */
    @field:Parameter(name = "size", description = "每页数据,默认10", example = "10", required = false)
    @field:QueryParam("size")
    @field:NotNull(message = "每页条数不能为空")
    @field:Min(value = 1, message = "每页条数最小值为 1")
    @field:Max(value = 100, message = "每页条数最大值为 100")
    var size: Int = PAGE_SIZE
) : Serializable {
    // 用于获取调整后的页码（适用于 Panache）
    fun getAdjustedPageNo(): Int = index - 1

    companion object {
        const val PAGE_NO = 1
        const val PAGE_SIZE = 10
    }
}