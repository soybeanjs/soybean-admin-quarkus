/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.infrastructure.persistence.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonStringListConverter : AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(attribute: List<String>?): String? =
        when {
            attribute.isNullOrEmpty() -> null
            else -> attribute.joinToString(separator = ",")
        }

    override fun convertToEntityAttribute(dbData: String?): List<String> =
        when {
            dbData.isNullOrBlank() -> emptyList()
            else -> dbData.split(",").map { it.trim() }
        }
}
