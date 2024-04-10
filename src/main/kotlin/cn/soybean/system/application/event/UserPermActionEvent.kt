package cn.soybean.system.application.event

import cn.soybean.shared.domain.event.DomainEvent

data class UserPermActionEvent(val userId: String) : DomainEvent
