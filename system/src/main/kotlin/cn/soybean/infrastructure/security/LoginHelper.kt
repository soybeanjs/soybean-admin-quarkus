/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.infrastructure.security

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.jwt.Claim
import org.eclipse.microprofile.jwt.Claims

@RequestScoped
class LoginHelper {
    @Inject
    @Claim(standard = Claims.sub)
    private lateinit var subject: String

    @Inject
    @Claim(standard = Claims.upn)
    private lateinit var upn: String

    @Inject
    @Claim("tenantId")
    private lateinit var tenant: String

    @Inject
    @Claim(standard = Claims.groups)
    private lateinit var groups: Set<Any>

    fun getUserId(): String = subject

    fun getAccountName(): String = upn

    fun getTenantId(): String = tenant

    fun getRoles(): Set<Any> = groups

    companion object {
        const val TENANT_KEY: String = "tenantId"
        const val USER_KEY: String = "userId"
        const val DEPT_KEY: String = "deptId"
        const val USER_AVATAR: String = "avatar"
    }
}
