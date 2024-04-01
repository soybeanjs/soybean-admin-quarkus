package cn.soybean.system.application.event

import cn.soybean.domain.DomainEvent
import cn.soybean.system.infrastructure.web.ApiEndpoint

data class ApiEndpointEvent(val data: Set<ApiEndpoint>) : DomainEvent