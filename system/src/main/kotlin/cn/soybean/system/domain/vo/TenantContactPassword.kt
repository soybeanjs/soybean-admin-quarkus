package cn.soybean.system.domain.vo

object TenantContactPassword {
    fun genPass(name: String): String = "$name@123456."
}