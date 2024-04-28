package cn.soybean.system.application.command.tenant

import cn.soybean.shared.application.command.Command

data class DeleteTenantCommand(val ids: Set<String>) : Command