/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.query.tenant.handler

import cn.soybean.domain.system.entity.SystemTenantEntity
import cn.soybean.domain.system.repository.SystemTenantRepository
import cn.soybean.interfaces.rest.dto.response.PageResult
import cn.soybean.system.application.query.tenant.PageTenantQuery
import cn.soybean.system.application.query.tenant.TenantByIdBuiltInQuery
import cn.soybean.system.application.query.tenant.TenantByIdQuery
import cn.soybean.system.application.query.tenant.TenantByNameExistsQuery
import cn.soybean.system.application.query.tenant.TenantByNameQuery
import cn.soybean.system.application.query.tenant.service.TenantQueryService
import cn.soybean.system.interfaces.rest.dto.response.tenant.TenantResponse
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

fun SystemTenantEntity.toTenantResponse(): TenantResponse =
    TenantResponse(
        name = name,
        contactAccountName = contactAccountName,
        status = status,
        website = website,
        expireTime = expireTime,
        menuIds = menuIds,
        operationIds = operationIds,
    )

@ApplicationScoped
class TenantQueryHandler(
    private val systemTenantRepository: SystemTenantRepository,
) : TenantQueryService {
    override fun handle(query: TenantByNameQuery): Uni<SystemTenantEntity> = systemTenantRepository.findByName(query.name)

    override fun handle(query: PageTenantQuery): Uni<PageResult<TenantResponse>> {
        val (q, params, page) = query
        val panacheQuery = systemTenantRepository.getTenantList(q, params).page(page)
        return panacheQuery.list().flatMap { resultList ->
            panacheQuery.count().map { count ->
                PageResult(resultList.map { it.toTenantResponse() }, page.index + 1, page.size, count)
            }
        }
    }

    override fun handle(query: TenantByNameExistsQuery): Uni<Boolean> = systemTenantRepository.existsByName(query.name)

    override fun handle(query: TenantByIdBuiltInQuery): Uni<Boolean> = systemTenantRepository.getById(query.id).map { it.builtIn }

    override fun handle(query: TenantByIdQuery): Uni<SystemTenantEntity> = systemTenantRepository.getById(query.id)
}
