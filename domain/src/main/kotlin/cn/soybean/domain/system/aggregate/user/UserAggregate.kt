/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.domain.system.aggregate.user

import cn.soybean.domain.system.enums.DbEnums
import cn.soybean.domain.system.event.user.UserCreatedOrUpdatedEventBase
import cn.soybean.domain.system.event.user.UserDeletedEventBase
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.util.SerializerUtils
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.elytron.security.common.BcryptUtil

class UserAggregate
@JsonCreator
constructor(
    @JsonProperty("aggregateId") aggregateId: String,
) :
    AggregateRoot(aggregateId, AGGREGATE_TYPE) {
    private var accountName: String? = null
    private var accountPassword: String? = null
    private var nickName: String? = null
    private var personalProfile: String? = null
    private var email: String? = null
    private var countryCode: String? = null
    private var phoneCode: String? = null
    private var phoneNumber: String? = null
    private var gender: DbEnums.Gender? = null
    private var avatar: String? = null
    private var deptId: String? = null
    private var status: DbEnums.Status? = null

    override fun whenCondition(eventEntity: AggregateEventEntity) {
        when (eventEntity.eventType) {
            UserCreatedOrUpdatedEventBase.USER_CREATED_V1, UserCreatedOrUpdatedEventBase.USER_UPDATED_V1 ->
                handle(
                    SerializerUtils.deserializeFromJsonBytes(
                        eventEntity.data,
                        UserCreatedOrUpdatedEventBase::class.java,
                    ),
                )

            UserDeletedEventBase.USER_DELETED_V1 ->
                SerializerUtils.deserializeFromJsonBytes(
                    eventEntity.data,
                    UserDeletedEventBase::class.java,
                )

            else -> throw RuntimeException(eventEntity.eventType)
        }
    }

    private fun handle(event: UserCreatedOrUpdatedEventBase) {
        this.accountName = event.accountName
        this.accountPassword = bcryptHashPassword(event.accountPassword)
        this.nickName = event.nickName
        this.personalProfile = event.personalProfile
        this.email = event.email
        this.countryCode = event.countryCode
        this.phoneCode = event.phoneCode
        this.phoneNumber = event.phoneNumber
        this.gender = event.gender
        this.avatar = event.avatar
        this.deptId = event.deptId
        this.status = event.status
    }

    fun createUser(data: UserCreatedOrUpdatedEventBase) {
        data.accountPassword = bcryptHashPassword(data.accountPassword)
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEventBase.USER_CREATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun updateUser(data: UserCreatedOrUpdatedEventBase) {
        data.accountPassword = bcryptHashPassword(data.accountPassword)
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserCreatedOrUpdatedEventBase.USER_UPDATED_V1, dataBytes, null)
        this.apply(event)
    }

    fun deleteUser(data: UserDeletedEventBase) {
        val dataBytes = SerializerUtils.serializeToJsonBytes(data)
        val event = this.createEvent(UserDeletedEventBase.USER_DELETED_V1, dataBytes, null)
        this.apply(event)
    }

    companion object {
        const val AGGREGATE_TYPE: String = "UserAggregate"
    }
}

fun bcryptHashPassword(password: String): String = BcryptUtil.bcryptHash(password)
