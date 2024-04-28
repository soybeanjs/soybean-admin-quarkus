package cn.soybean.system.application.query.tenant

import cn.soybean.shared.application.query.Query
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters

data class TenantByNameQuery(val name: String) : Query
data class PageTenantQuery(val query: String, val params: Parameters, val page: Page) : Query
data class TenantByNameExistsQuery(val name: String) : Query
data class TenantByIdBuiltInQuery(val id: String) : Query