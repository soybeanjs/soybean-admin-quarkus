package cn.soybean.framework.common.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonToListConverter : AttributeConverter<List<String>, String> {

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