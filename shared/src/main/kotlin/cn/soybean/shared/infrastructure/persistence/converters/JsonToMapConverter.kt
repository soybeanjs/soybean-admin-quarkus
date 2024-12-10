/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.infrastructure.persistence.converters

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonToMapConverter(private val objectMapper: ObjectMapper = jacksonObjectMapper()) :
    AttributeConverter<Map<String, Any>, String> {
    override fun convertToDatabaseColumn(attribute: Map<String, Any>): String = objectMapper.writeValueAsString(attribute)

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any> = when {
        dbData.isNullOrBlank() -> emptyMap()

        else -> objectMapper.readValue(dbData, object : TypeReference<Map<String, Any>>() {})
    }
}
