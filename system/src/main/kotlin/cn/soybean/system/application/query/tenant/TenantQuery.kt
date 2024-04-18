package cn.soybean.system.application.query.tenant

import cn.soybean.shared.application.query.Query

data class TenantByNameQuery(val name: String) : Query