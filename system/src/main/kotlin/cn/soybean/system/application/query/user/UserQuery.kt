package cn.soybean.system.application.query.user

import cn.soybean.shared.application.query.Query
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters

data class PageUserQuery(val query: String, val params: Parameters, val page: Page) : Query
data class UserById(val id: String, val tenantId: String) : Query
data class UserByaAccountName(val accountName: String, val tenantId: String) : Query
data class UserByPhoneNumber(val phoneNumber: String, val tenantId: String) : Query
data class UserByEmail(val email: String, val tenantId: String) : Query
data class UserByIdBuiltInQuery(val id: String, val tenantId: String) : Query