package cn.soybean.infrastructure.interceptor.dto

import cn.soybean.system.domain.entity.SystemOperationLogEntity
import java.time.LocalDateTime

data class OperationLogDTO(
    val userId: String?,
    val accountName: String?,
    val tenantId: String?,
    val moduleName: String,
    val description: String,
    /**
     * 请求方法名
     */
    var method: String,

    /**
     * 请求地址
     */
    val path: String,

    /**
     * 用户 IP
     */
    val userIp: String,

    /**
     * 浏览器 UserAgent
     */
    val userAgent: String,

    /**
     * resourceClass 类名
     */
    val resourceClass: String,

    /**
     * resourceMethod 方法名
     */
    val resourceMethod: String,

    /**
     * 开始时间
     */
    val startTime: LocalDateTime,
    val requestInfo: String?,
    val responseInfo: String?,
    val processingTime: String,
    val resultCode: Int?,
    val resultMsg: String?,
) {
    fun toOperateLogEntity(): SystemOperationLogEntity {
        val operateLogEntity = SystemOperationLogEntity(
            traceId = null,
            userId = this.userId,
            accountName = this.accountName,
            tenantId = this.tenantId,
            moduleName = this.moduleName,
            description = this.description,
            method = this.method,
            path = this.path,
            userIp = this.userIp,
            userAgent = this.userAgent,
            resource = "${this.resourceClass}.${this.resourceMethod}",
            requestInfo = this.requestInfo,
            startTime = this.startTime,
            duration = this.processingTime,
            resultCode = this.resultCode,
            resultMsg = this.resultMsg,
            resultData = this.responseInfo
        )
        return operateLogEntity
    }
}
