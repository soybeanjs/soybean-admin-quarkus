package cn.soybean.example

import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping
import io.mcarle.konvert.api.converter.STRING_TO_INT_CONVERTER

@KonvertTo(
    AddressDto::class,
    mappings = [
        Mapping(source = "street", target = "streetName"),
        Mapping(source = "zip", target = "zipCode"),
        Mapping(source = "streetNumber", target = "streetNumber", enable = [STRING_TO_INT_CONVERTER]),
    ],
    priority = 1
)
data class Address(
    val street: String,
    val streetNumber: String,
    val zip: String,
    val city: String,
    val country: String
)

@KonvertTo(
    Address::class,
    mappings = [
        Mapping(source = "streetName", target = "street"),
        Mapping(source = "zipCode", target = "zip")
    ]
)
data class AddressDto(
    val streetName: String,
    val streetNumber: Int,
    val zipCode: String,
    val city: String,
    val country: String
)
