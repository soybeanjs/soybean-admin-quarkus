package cn.soybean.domain.system.vo

object TenantContactPassword {
    fun genPass(name: String): String = "$name@123456."
}
