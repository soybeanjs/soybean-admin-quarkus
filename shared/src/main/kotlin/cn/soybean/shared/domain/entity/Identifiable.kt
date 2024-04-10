package cn.soybean.shared.domain.entity

interface Identifiable<ID : Any> {
    val id: ID
}