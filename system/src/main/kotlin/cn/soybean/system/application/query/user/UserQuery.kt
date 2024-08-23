package cn.soybean.system.application.query.user

import cn.soybean.shared.application.query.Query
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters

data class PageUserQuery(val query: String, val params: Parameters, val page: Page) : Query
data class UserByIdQuery(val id: String, val tenantId: String) : Query
data class UserByaAccountNameQuery(val accountName: String, val tenantId: String) : Query
data class UserByPhoneNumberQuery(val phoneNumber: String, val tenantId: String) : Query
data class UserByEmailQuery(val email: String, val tenantId: String) : Query
data class UserByIdBuiltInQuery(val id: String, val tenantId: String) : Query
data class UserByAccountQuery(val accountName: String, val tenantId: String) : Query
data class UserByTenantIdQuery(val tenantId: String) : Query
