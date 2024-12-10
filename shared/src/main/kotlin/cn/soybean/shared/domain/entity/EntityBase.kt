/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.shared.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
open class EntityBase<ID : Any> : Identifiable<ID> {
    @Id
    @field:Column(name = "id", unique = true, nullable = false)
    override lateinit var id: ID

    override fun toString() = "${javaClass.simpleName}<$id>"
}
