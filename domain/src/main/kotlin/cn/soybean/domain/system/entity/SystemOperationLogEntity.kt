package cn.soybean.domain.system.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "sys_operation_log")
@SequenceGenerator(name = "sys_operation_log_seq", sequenceName = "sys_operation_log_seq", allocationSize = 1)
open class SystemOperationLogEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_operation_log_seq")
    val id: Long? = null,

    // 链路追踪编号
    @Column(name = "trace_id")
    val traceId: String? = null,

    // 用户编号
    @Column(name = "user_id")
    val userId: String? = null,

    @Column(name = "account_name")
    val accountName: String? = null,

    @Column(name = "tenant_id")
    val tenantId: String? = null,

    // 操作模块
    @Column(name = "module_name")
    val moduleName: String? = null,

    // 操作名
    val description: String? = null,

    // 请求方法名
    @Column(name = "method")
    val method: String? = null,

    // 请求地址
    @Column(name = "path")
    val path: String? = null,

    // 用户 IP
    @Column(name = "user_ip")
    val userIp: String? = null,

    // 浏览器 UA
    @Column(name = "user_agent")
    val userAgent: String? = null,

    // resource
    @Column(name = "resource")
    val resource: String? = null,

    // requestInfo
    @Column(name = "request_info")
    val requestInfo: String? = null,

    // 开始时间
    @Column(name = "start_time")
    val startTime: LocalDateTime? = null,

    // 执行时长
    val duration: String? = null,

    // 结果码
    @Column(name = "result_code")
    val resultCode: Int? = null,

    // 结果提示
    @Column(name = "result_msg")
    val resultMsg: String? = null,

    // 结果数据
    @Column(name = "result_data")
    val resultData: String? = null,

    @CreationTimestamp
    @Column(name = "create_time")
    val createTime: LocalDateTime? = null
)
