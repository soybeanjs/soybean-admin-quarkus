/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import java.io.Serializable

@RegisterForReflection
class ResponseEntity<T>(
    @JsonProperty("code") val code: String,
    @JsonProperty("msg") val msg: String,
    @JsonProperty("data") val data: T? = null,
) : Serializable {
    companion object {
        private const val SUCCESS = "0000"
        private const val FAIL = "-1"
        private const val MSG_SUCCESS = "success"
        private const val MSG_FAIL = "fail"

        private fun <T> response(
            code: String,
            msg: String = MSG_SUCCESS,
            data: T? = null,
        ) = ResponseEntity(code, msg, data)

        fun <T> ok(data: T? = null) = response(SUCCESS, MSG_SUCCESS, data)

        fun <T> ok(
            msg: String,
            data: T? = null,
        ) = response(SUCCESS, msg, data)

        fun <T> fail(data: T? = null) = response(FAIL, MSG_FAIL, data)

        fun <T> fail(
            msg: String,
            data: T? = null,
        ) = response(FAIL, msg, data)

        fun <T> error(
            code: String,
            msg: String,
            data: T? = null,
        ) = response(code, msg, data)
    }
}
