package cn.soybean.system.application.query

import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters

data class PageUserQuery(val query: String, val params: Parameters, val page: Page)