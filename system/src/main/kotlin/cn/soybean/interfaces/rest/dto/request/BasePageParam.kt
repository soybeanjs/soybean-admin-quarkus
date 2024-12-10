/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest.dto.request

import io.quarkus.panache.common.Page
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
    @field:NotNull(message = "{validation.page.index.NotNull}")
    @field:Min(value = 1, message = "{validation.page.index.Min}")
    var index: Int = PAGE_NO,
    /**
     * See [io.quarkus.panache.common.Page.size]
     */
    @field:Parameter(name = "size", description = "每页数据,默认10", example = "10", required = false)
    @field:QueryParam("size")
    @field:NotNull(message = "{validation.page.size.NotNull}")
    @field:Min(value = 1, message = "{validation.page.size.Min}")
    @field:Max(value = 100, message = "{validation.page.size.Max}")
    var size: Int = PAGE_SIZE,
) : Serializable {
    // 用于获取调整后的页码（适用于 Panache）
    private fun getAdjustedPageNo(): Int = index - 1

    fun ofPage(): Page = Page.of(getAdjustedPageNo(), size)

    companion object {
        const val PAGE_NO = 1
        const val PAGE_SIZE = 10
    }
}
