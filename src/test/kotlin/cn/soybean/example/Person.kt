package cn.soybean.example

import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping
import io.mcarle.konvert.api.converter.LONG_TO_UINT_CONVERTER

@KonvertTo(
    value = PersonDto::class,
    mappings = [
        Mapping(source = "age", target = "numberOfYearsSinceBirth", enable = [LONG_TO_UINT_CONVERTER]),
    ]
)
@KonvertTo(value = PersonDao::class)
data class Person(
    val name: String,
    val address: Address,
    val age: Long
)
