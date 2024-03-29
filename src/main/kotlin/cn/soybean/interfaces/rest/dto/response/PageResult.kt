package cn.soybean.interfaces.rest.dto.response

import cn.soybean.interfaces.rest.dto.request.BasePageParam.Companion.PAGE_NO
import cn.soybean.interfaces.rest.dto.request.BasePageParam.Companion.PAGE_SIZE
import io.quarkus.runtime.annotations.RegisterForReflection
import java.io.Serializable

@RegisterForReflection
data class PageResult<T>(
    val records: List<T>,
    val current: Int,
    val size: Int,
    val total: Long
) : Serializable {
    companion object {
        fun <T> empty(): PageResult<T> = PageResult(listOf(), PAGE_NO, PAGE_SIZE, 0L)
    }

    constructor(current: Int, size: Int) : this(listOf(), current, size, 0L)
}