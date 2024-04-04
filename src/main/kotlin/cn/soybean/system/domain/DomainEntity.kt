package cn.soybean.system.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
open class DomainEntity : PanacheEntityBase {

    @Id
    @field:Column(name = "id", unique = true, nullable = false)
    open lateinit var id: String

    override fun toString() = "${javaClass.simpleName}<$id>"
}