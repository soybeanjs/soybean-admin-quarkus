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