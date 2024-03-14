package cn.soybean.example

import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    value = Person::class,
    mappings = [
        Mapping(source = "numberOfYearsSinceBirth", target = "age")
    ]
)
data class PersonDto(
    val name: String,
    val address: AddressDto,
    val numberOfYearsSinceBirth: UInt
)
