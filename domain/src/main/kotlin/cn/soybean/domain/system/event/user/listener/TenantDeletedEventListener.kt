/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.event.user.listener

import cn.soybean.domain.system.event.tenant.TenantDeletedEventBase
import cn.soybean.shared.domain.event.DomainEvent
import cn.soybean.shared.domain.event.DomainEventListener
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TenantDeletedEventListener : DomainEventListener<TenantDeletedEventBase> {
    override fun onEvent(event: TenantDeletedEventBase) {
        println("此处案例来说接受到租户删除事件要做处理,但感觉有些多租户的逻辑删换或者禁用租户十个很敏感的操作,所以暂时只打印,具体实现后面再说")
    }

    override fun supports(event: DomainEvent): Boolean = event is TenantDeletedEventBase
}
