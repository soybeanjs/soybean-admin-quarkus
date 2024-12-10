/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.event.user

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.event.DomainEvent

data class UserCreatedOrUpdatedEventBase(
    val aggregateId: String,
    val accountName: String,
    var accountPassword: String,
    val nickName: String,
    val personalProfile: String? = null,
    val email: String? = null,
    val countryCode: String,
    val phoneCode: String,
    val phoneNumber: String? = null,
    val gender: DbEnums.Gender? = null,
    val avatar: String? = null,
    val deptId: String? = null,
    val status: DbEnums.Status,
) : AggregateEventBase(aggregateId), DomainEvent {
    companion object {
        const val USER_CREATED_V1 = "USER_CREATED_V1"
        const val USER_UPDATED_V1 = "USER_UPDATED_V1"
    }
}
