package cn.soybean.system.application.event

import cn.soybean.domain.DomainEvent

data class UserPermActionEvent(val userId: String) : DomainEvent
