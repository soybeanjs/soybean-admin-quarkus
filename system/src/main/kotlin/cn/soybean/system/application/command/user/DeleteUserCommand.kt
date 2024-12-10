/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.application.command.user

import cn.soybean.shared.application.command.Command

data class DeleteUserCommand(
    val ids: Set<String>,
) : Command
