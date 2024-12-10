/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.infrastructure.persistence

import io.quarkus.panache.common.Parameters

class QueryBuilder(tenantId: String) {
    private val conditions = mutableListOf<String>()
    private val parameters = mutableMapOf<String, Any>()

    /**
     * 目前panache reactive模式下对多租户的支持暂时只能通过手动添加tenantId条件来实现字段级别租户
     * 非reactive模式支持 独立数据库 schema 字段级别 的多租户
     */
    init {
        conditions.add("tenantId = :tenantId")
        parameters["tenantId"] = tenantId
    }

    fun addCondition(field: String, value: Any?, operator: String = "=", prefix: String = "AND"): QueryBuilder {
        value?.let {
            val condition = "$field $operator :$field"
            val wrappedCondition = if (conditions.isNotEmpty()) " $prefix $condition" else condition
            conditions.add(wrappedCondition)
            parameters[field] = it
        }
        return this
    }

    fun addLikeCondition(field: String, value: String?): QueryBuilder = addCondition(field, value?.let { "%$it%" }, "LIKE")

    fun addCompositeCondition(composite: () -> QueryBuilder, prefix: String = "AND"): QueryBuilder {
        val builder = composite()
        if (builder.conditions.isNotEmpty()) {
            val compositeCondition = "(${builder.conditions.joinToString(" ")})"
            conditions.add(if (conditions.isNotEmpty()) " $prefix $compositeCondition" else compositeCondition)
            parameters.putAll(builder.parameters)
        }
        return this
    }

    fun build(): Pair<String, Map<String, Any>> = conditions.joinToString(" ") to parameters

    fun buildParameters(): Pair<String, Parameters> {
        val params = Parameters()
        parameters.forEach { (key, value) ->
            params.and(key, value)
        }
        return conditions.joinToString(" ") to params
    }
}
