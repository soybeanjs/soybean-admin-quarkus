/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.event

import cn.soybean.shared.domain.event.DomainEvent
import cn.soybean.system.infrastructure.web.ApiEndpoint

data class ApiEndpointEvent(val data: Set<ApiEndpoint>) : DomainEvent
