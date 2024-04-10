package cn.soybean.shared.util

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import java.io.IOException
import java.nio.charset.StandardCharsets

object SerializerUtils {
    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerModule(ParameterNamesModule())
        registerModule(Jdk8Module())
        registerModule(JavaTimeModule())
    }

    @JvmStatic
    fun serializeToJsonBytes(obj: Any): ByteArray = try {
        objectMapper.writeValueAsBytes(obj)
    } catch (e: JsonProcessingException) {
        throw RuntimeException(e.message, e)
    }

    @JvmStatic
    fun <T> deserializeFromJsonBytes(jsonBytes: ByteArray, valueType: Class<T>): T = try {
        objectMapper.readValue(jsonBytes, valueType)
    } catch (e: IOException) {
        throw RuntimeException(e.message, e)
    }

    @JvmStatic
    fun deserializeEventsFromJsonBytes(jsonBytes: ByteArray): Array<AggregateEventEntity> = try {
        objectMapper.readValue(jsonBytes, Array<AggregateEventEntity>::class.java)
    } catch (e: IOException) {
        throw RuntimeException(e.message, e)
    }

    @JvmStatic
    fun deserializeEventsMetadata(metaData: ByteArray): HashMap<String, ByteArray> {
        val tr = object : TypeReference<HashMap<String, ByteArray>>() {}
        return try {
            objectMapper.readValue(metaData, tr)
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }
    }

    @JvmStatic
    fun serializeEventsMetadata(metaData: HashMap<String, ByteArray>): ByteArray = try {
        val valueAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metaData)
        valueAsString.toByteArray(StandardCharsets.UTF_8)
    } catch (e: IOException) {
        throw RuntimeException(e.message, e)
    }
}