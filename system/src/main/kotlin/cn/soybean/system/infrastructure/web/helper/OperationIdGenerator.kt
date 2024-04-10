package cn.soybean.system.infrastructure.web.helper

import java.security.MessageDigest

val md5DigestInstance: ThreadLocal<MessageDigest>
    get() = ThreadLocal.withInitial {
        MessageDigest.getInstance("MD5")
    }

fun generateOperationId(httpMethod: String, path: String): String {
    val input = "$httpMethod$path"

    val hashBytes = md5DigestInstance.get().digest(input.toByteArray())

    val hexString = hashBytes.joinToString(separator = "") { byte ->
        "%02X".format(byte)
    }

    return "OP_$hexString"
}