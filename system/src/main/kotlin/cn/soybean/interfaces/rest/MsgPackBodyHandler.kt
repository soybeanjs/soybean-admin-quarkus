/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.MultivaluedMap
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.server.spi.ResteasyReactiveResourceInfo
import org.jboss.resteasy.reactive.server.spi.ServerMessageBodyWriter
import org.jboss.resteasy.reactive.server.spi.ServerRequestContext
import org.msgpack.jackson.dataformat.MessagePackFactory
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.lang.reflect.Type

@Provider
@Produces("application/x-msgpack")
@Consumes(MediaType.APPLICATION_JSON)
class MsgPackBodyHandler : ServerMessageBodyWriter<Any> {
    private val objectMapper: ObjectMapper = ObjectMapper(MessagePackFactory().setReuseResourceInGenerator(false))

    override fun isWriteable(
        type: Class<*>?,
        genericType: Type?,
        target: ResteasyReactiveResourceInfo?,
        mediaType: MediaType?,
    ): Boolean = true

    override fun isWriteable(
        type: Class<*>?,
        genericType: Type?,
        annotations: Array<out Annotation>?,
        mediaType: MediaType?,
    ): Boolean = true

    override fun writeResponse(
        o: Any?,
        genericType: Type?,
        context: ServerRequestContext?,
    ) {
        context?.let { ctx ->
            val outputStream = ByteArrayOutputStream()
            writeTo(o, o?.javaClass, genericType, null, null, null, outputStream)
            ctx.serverResponse().end(outputStream.toByteArray())
        }
    }

    override fun writeTo(
        t: Any?,
        type: Class<*>?,
        genericType: Type?,
        annotations: Array<out Annotation>?,
        mediaType: MediaType?,
        httpHeaders: MultivaluedMap<String, Any>?,
        entityStream: OutputStream?,
    ) {
        entityStream?.let { stream ->
            val bytes = objectMapper.writeValueAsBytes(t)
            stream.write(bytes)
        }
    }
}
