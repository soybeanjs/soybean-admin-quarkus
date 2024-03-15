package cn.soybean.framework.common.base

import io.quarkus.runtime.annotations.RegisterForReflection
import java.io.Serializable

@RegisterForReflection
class ResponseEntity(
    val code: String,
    val msg: String,
    val data: Any? = null
) : Serializable {

    companion object {
        private const val SUCCESS = "0000"
        private const val FAIL = "-1"
        private const val MSG_SUCCESS = "success"
        private const val MSG_FAIL = "fail"

        private fun response(code: String, msg: String = MSG_SUCCESS, data: Any? = null) =
            ResponseEntity(code, msg, data)

        fun <Any> ok(data: Any? = null) = response(SUCCESS, MSG_SUCCESS, data)

        fun <Any> ok(msg: String, data: Any? = null) = response(SUCCESS, msg, data)

        fun <Any> fail(data: Any? = null) = response(FAIL, MSG_FAIL, data)

        fun <Any> fail(msg: String, data: Any? = null) = response(FAIL, msg, data)

        fun <Any> error(code: String, msg: String, data: Any? = null) = response(code, msg, data)
    }
}