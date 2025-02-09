/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.infrastructure.persistence.repository

import cn.soybean.domain.system.entity.SystemDeptEntity
import cn.soybean.domain.system.repository.SystemDeptRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemDeptRepositoryImpl :
    SystemDeptRepository,
    PanacheRepositoryBase<SystemDeptEntity, String>
