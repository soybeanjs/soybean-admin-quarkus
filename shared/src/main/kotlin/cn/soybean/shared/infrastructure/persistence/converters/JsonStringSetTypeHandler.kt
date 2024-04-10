package cn.soybean.shared.infrastructure.persistence.converters

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonStringSetTypeHandler(private val objectMapper: ObjectMapper = jacksonObjectMapper()) :
    AttributeConverter<Set<String>, String> {

    override fun convertToDatabaseColumn(attribute: Set<String>?): String = when (attribute) {
        null -> "[]"
        else -> try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting set of Strings to JSON", e)
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Set<String> = when {
        dbData.isNullOrBlank() -> emptySet()
        else -> try {
            objectMapper.readValue(dbData, object : TypeReference<Set<String>>() {})
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting JSON to set of Strings", e)
        }
    }
}