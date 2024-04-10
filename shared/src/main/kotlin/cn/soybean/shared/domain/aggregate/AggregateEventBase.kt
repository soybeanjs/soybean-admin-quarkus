package cn.soybean.shared.domain.aggregate

abstract class AggregateEventBase(aggregateId: String) {
    init {
        require(aggregateId.isNotBlank()) { "AggregateEventBase aggregateId is required" }
    }

    open val eventType: String = this::class.simpleName ?: "UnknownEventType"

    var tenantId: String? = null
    var createBy: String? = null
    var createAccountName: String? = null
    var updateBy: String? = null
    var updateAccountName: String? = null
}