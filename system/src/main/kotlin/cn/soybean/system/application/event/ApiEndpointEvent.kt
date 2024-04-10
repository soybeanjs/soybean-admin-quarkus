package cn.soybean.system.application.event

import cn.soybean.shared.domain.event.DomainEvent
import cn.soybean.system.infrastructure.web.ApiEndpoint

data class ApiEndpointEvent(val data: Set<ApiEndpoint>) : DomainEvent