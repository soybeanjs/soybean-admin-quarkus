package cn.soybean.system.infrastructure.util

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.system.infrastructure.util.SignUtil.createSign
import cn.soybean.system.infrastructure.util.SignUtil.getRandomString
import org.jetbrains.annotations.Contract
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.Instant
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.function.Consumer
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun main() {
    val paramMap: MutableMap<String, Any> = LinkedHashMap()
    paramMap[AppConstants.API_KEY] = "1"
    paramMap[AppConstants.API_TIMESTAMP] = Instant.now().toEpochMilli()
    paramMap[AppConstants.API_NONCE] = getRandomString(32)
    // 更多参数，不限制数量...

    // 补全 timestamp、nonce、sign 参数，并序列化为 kv 字符串
    val createSign = createSign(paramMap, "HmacSHA256", "1")
    println(paramMap)
    println(createSign)
}

object SignUtil {

    private const val SECRET_KEY = "1"
    private const val MD5 = "MD5"
    private const val SHA1 = "SHA1"
    private const val SHA_256 = "SHA-256"
    private const val HMAC_SHA_256 = "HmacSHA256"

    /**
     * 给 paramsMap 追加 timestamp、nonce、sign 三个参数，并转换为参数字符串，形如：
     * `data=xxx8nonce=xxx8timestamp=xxx8sign=xxx`
     *
     * @param paramsMap 参数列表
     * @return 加工后的参数列表 转化为的参数字符串
     */
    @Throws(NoSuchAlgorithmException::class)
    fun addSignParamsAndJoin(paramsMap: MutableMap<String, Any>, algorithm: String?): String {
        // 追加参数
        addSignParams(paramsMap, algorithm)

        // 拼接参数
        return joinParams(paramsMap)
    }

    /**
     * 给 paramsMap 追加 timestamp、nonce、sign 三个参数
     *
     * @param paramsMap 参数列表
     */
    @Throws(NoSuchAlgorithmException::class)
    fun addSignParams(paramsMap: MutableMap<String, Any>, algorithm: String?) {
        paramsMap[AppConstants.API_TIMESTAMP] = Instant.now().toEpochMilli()
        paramsMap[AppConstants.API_NONCE] = getRandomString(32)
        paramsMap[AppConstants.API_SIGNATURE] = createSign(paramsMap, algorithm)
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串的长度
     * @return 一个随机字符串
     */
    fun getRandomString(length: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val sb = StringBuilder()
        for (i in 0 until length) {
            val number = ThreadLocalRandom.current().nextInt(62)
            sb.append(str[number])
        }
        return sb.toString()
    }

    /**
     * 创建签名：md5(paramsStr + keyStr)
     *
     * @param paramsMapIn 参数列表
     * @return 签名
     */
    @Throws(NoSuchAlgorithmException::class)
    fun createSign(paramsMapIn: MutableMap<String, *>, algorithm: String?): String {
        // 如果调用者不小心传入了 sign 参数，则此处需要将 sign 参数排除在外
        var paramsMap = paramsMapIn
        if (paramsMap.containsKey(AppConstants.API_SIGNATURE)) {
            // 为了保证不影响原有的 paramsMap，此处需要再复制一份
            paramsMap = TreeMap(paramsMap)
            paramsMap.remove(AppConstants.API_SIGNATURE)
        }

        // 计算签名
        val paramsStr = joinParamsDictSort(paramsMap)
        val fullStr = "$paramsStr&${AppConstants.API_SECRET}=$SECRET_KEY"
        return when (algorithm) {
            MD5 -> md5(fullStr)

            SHA1 -> sha1(fullStr)

            SHA_256 -> sha256(fullStr)

            HMAC_SHA_256 -> hmacSHA256(paramsStr, SECRET_KEY)

            else -> throw RuntimeException("签名非法")
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    fun createSign(paramsMapIn: MutableMap<String, *>, algorithm: String?, secretKey: String): String {
        // 如果调用者不小心传入了 sign 参数，则此处需要将 sign 参数排除在外
        var paramsMap = paramsMapIn
        if (paramsMap.containsKey(AppConstants.API_SIGNATURE)) {
            // 为了保证不影响原有的 paramsMap，此处需要再复制一份
            paramsMap = TreeMap(paramsMap)
            paramsMap.remove(AppConstants.API_SIGNATURE)
        }

        // 计算签名
        val paramsStr = joinParamsDictSort(paramsMap)
        val fullStr = "$paramsStr&${AppConstants.API_SECRET}=$secretKey"
        return when (algorithm) {
            MD5 -> md5(fullStr)

            SHA1 -> sha1(fullStr)

            SHA_256 -> sha256(fullStr)

            HMAC_SHA_256 -> hmacSHA256(paramsStr, secretKey)

            else -> throw RuntimeException("签名非法")
        }
    }

    /**
     * 将所有参数按照字典顺序连接成一个字符串，形如：a=18b=28c=3
     *
     * @param paramsMapIn 参数列表
     * @return 拼接出的参数字符串
     */
    private fun joinParamsDictSort(paramsMapIn: Map<String, *>?): String {
        // 保证字段按照字典顺序排列
        var paramsMap = paramsMapIn
        if (paramsMap !is TreeMap<*, *>) {
            paramsMap = TreeMap(paramsMap)
        }

        // 拼接
        return joinParams(paramsMap)
    }

    /**
     * 将所有参数连接成一个字符串(不排序)，形如：b=28a=18c=3
     *
     * @param paramsMap 参数列表
     * @return 拼接出的参数字符串
     */
    private fun joinParams(paramsMap: Map<String, *>): String {

        // 按照 k1=v1&k2=v2&k3=v3 排列
        val sb = StringBuilder()
        paramsMap.keys.forEach(Consumer { key: String ->
            val value = paramsMap[key]
            if (!isEmpty(value)) {
                sb.append(key).append("=").append(value).append("&")
            }
        })

        // 删除最后一位 &
        if (sb.isNotEmpty()) {
            sb.deleteCharAt(sb.length - 1)
        }

        // .
        return sb.toString()
    }

    /**
     * 指定元素是否为null或者空字符串
     *
     * @param str 指定元素
     * @return 是否为null或者空字符串
     */
    private fun isEmpty(str: Any?): Boolean {
        return str == null || "" == str
    }

    /**
     * md5加密
     *
     * @param strIn 指定字符串
     * @return 加密后的字符串
     */
    @Contract("_ -> new")
    @Throws(NoSuchAlgorithmException::class)
    fun md5(strIn: String?): String {
        val str = strIn ?: ""
        val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        val btInput = str.toByteArray()
        val mdInst = MessageDigest.getInstance(MD5)
        mdInst.update(btInput)
        val md = mdInst.digest()
        val j = md.size
        val strA = CharArray(j * 2)
        var k = 0
        for (byte0 in md) {
            strA[k++] = hexDigits[byte0.toInt() ushr 4 and 0xf]
            strA[k++] = hexDigits[byte0.toInt() and 0xf]
        }
        return String(strA)
    }

    /**
     * sha1加密
     *
     * @param strIn 指定字符串
     * @return 加密后的字符串
     */
    @Contract("_ -> new")
    @Throws(NoSuchAlgorithmException::class)
    fun sha1(strIn: String?): String {
        val str = strIn ?: ""
        val md = MessageDigest.getInstance(SHA1)
        val b = str.toByteArray()
        md.update(b)
        val b2 = md.digest()
        val len = b2.size
        val strA = "0123456789abcdef"
        val ch = strA.toCharArray()
        val chs = CharArray(len * 2)
        var i = 0
        var k = 0
        while (i < len) {
            val b3 = b2[i]
            chs[k++] = ch[b3.toInt() ushr 4 and 0xf]
            chs[k++] = ch[b3.toInt() and 0xf]
            i++
        }
        return String(chs)
    }

    /**
     * sha256加密
     *
     * @param strIn 指定字符串
     * @return 加密后的字符串
     */
    @Throws(NoSuchAlgorithmException::class)
    fun sha256(strIn: String?): String {
        val str = strIn ?: ""
        val messageDigest = MessageDigest.getInstance(SHA_256)
        messageDigest.update(str.toByteArray(StandardCharsets.UTF_8))
        val bytes = messageDigest.digest()
        val builder = StringBuilder()
        var temp: String
        for (aByte in bytes) {
            temp = Integer.toHexString(aByte.toInt() and 0xFF)
            if (temp.length == 1) {
                builder.append("0")
            }
            builder.append(temp)
        }
        return builder.toString()
    }

    @Throws(NoSuchAlgorithmException::class)
    fun hmacSHA256(strIn: String?, secret: String): String {
        val str = strIn ?: ""
        val secretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), HMAC_SHA_256)
        val mac = Mac.getInstance(HMAC_SHA_256)
        mac.init(secretKeySpec)
        val bytes = mac.doFinal(str.toByteArray(StandardCharsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
