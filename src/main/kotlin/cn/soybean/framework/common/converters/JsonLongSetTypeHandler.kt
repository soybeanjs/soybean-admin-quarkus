package cn.soybean.framework.common.converters

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonLongSetTypeHandler(private val objectMapper: ObjectMapper = jacksonObjectMapper()) :
    AttributeConverter<Set<Long>, String> {

    override fun convertToDatabaseColumn(attribute: Set<Long>?): String = when (attribute) {
        null -> "[]"
        else -> try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting set of Longs to JSON", e)
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Set<Long> = when {
        dbData.isNullOrBlank() -> emptySet()
        else -> try {
            objectMapper.readValue(dbData, object : TypeReference<Set<Long>>() {})
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting JSON to set of Longs", e)
        }
    }
}